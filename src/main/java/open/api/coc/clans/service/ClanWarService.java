
package open.api.coc.clans.service;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.config.HallOfFameConfig;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackPKEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberPKEntity;
import open.api.coc.clans.database.entity.clan.ClanWarRecordDTO;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.clan.ClanWarMemberQueryRepository;
import open.api.coc.clans.database.repository.clan.ClanWarMemberRepository;
import open.api.coc.clans.database.repository.clan.ClanWarQueryRepository;
import open.api.coc.clans.database.repository.clan.condition.ClanWarWhitelistQueryRepository;
import open.api.coc.clans.domain.clans.ClanWarMemberQuery;
import open.api.coc.clans.domain.clans.ClanWarMemberResponse;
import open.api.coc.clans.domain.clans.ClanWarMissingAttackPlayerDTO;
import open.api.coc.clans.domain.clans.ClanWarResponse;
import open.api.coc.clans.domain.clans.converter.EntityClanWarMemberResponseConverter;
import open.api.coc.clans.domain.clans.converter.EntityClanWarResponseConverter;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.clan.ClanWarAttack;
import open.api.coc.external.coc.clan.domain.clan.ClanWarMember;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanWarService {

    private final HallOfFameConfig hallOfFameConfig;

    private final ClanApiService clanApiService;
    private final ClanRepository clanRepository;

    private final ClanWarWhitelistQueryRepository clanWarWhitelistQueryRepository;

    private final ClanWarMemberRepository clanWarMemberRepository;

    private final ClanWarQueryRepository clanWarQueryRepository;
    private final ClanWarMemberQueryRepository clanWarMemberQueryRepository;

    private final TimeConverter timeConverter;
    private final EntityClanWarResponseConverter entityClanWarResponseConverter;
    private final EntityClanWarMemberResponseConverter entityClanWarMemberResponseConverter;

    private final String CLAN_WAR_ROOT_DIR = "./clan-war";
    private final String CLAN_WAR_GROUP_DIR = CLAN_WAR_ROOT_DIR + "/{clanTag}";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 클랜전 현황 메타 정보를 생성
     * 생성된 경우 상태값, 종료시간 갱신
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClanWarEntity mergeClanWar(ClanWar clanWar, ClanWarType clanWarType) {

        String clanTag = clanWar.getClan().getTag();

        Optional<ClanEntity> findClan = clanRepository.findById(clanTag);
        if (findClan.isEmpty()) { return ClanWarEntity.empty(); }
        ClanEntity clanEntity = findClan.get();
        if (clanEntity.isNotUsed()) { return ClanWarEntity.empty(); }

        LocalDateTime startTime = getLocalDateTime(clanWar.getStartTime());

        Optional<ClanWarEntity> findClanWar;
        if (ClanWarType.LEAGUE.equals(clanWarType)) {
            // 리그전의 경우 리그 전쟁 태그로 유일 데이터 판단
            findClanWar = clanWarQueryRepository.findByClanTagAndWarTag(clanWar.getClan().getTag(), clanWar.getWarTag());
        } else {
            // 일반 클랜전의 경우 클랜태그와 시작 시간으로 유일 데이터 판단
            findClanWar = clanWarQueryRepository.findByClanTagAndStartTime(clanTag, startTime);
        }

        // 클랜전 신규 기록 생성
        if (findClanWar.isEmpty()) {
            return saveClanWar(clanWar, clanWarType);
        }

        // 기존에 기록된 클랜전 정보 현행화
        ClanWarEntity clanWarEntity = findClanWar.get();

        LocalDateTime endTime = getLocalDateTime(clanWar.getEndTime());
        // 점검 등으로 종료일시가 변경된 경우 갱신
        if (!getLocalDateTime(clanWar.getEndTime()).isEqual(clanWarEntity.getEndTime())) {
            clanWarEntity.setEndTime(endTime);
        } else if (clanWarEntity.isCollected()) {
            // 이미 수집된 경우
            return ClanWarEntity.empty();
        }

        clanWarEntity.setState(clanWar.getState());

        return clanWarEntity;
    }

    @Transactional
    public void mergeClanWarLeague(String clanTag, String warTag, ClanWar clanWar) {
        // 처리를 위한 데이터 설정
        clanWar.setBattleModifier("none");
        clanWar.setWarTag(warTag);

        clanWar.swapWarClan(clanTag);

        ClanWarEntity clanWarEntity = mergeClanWar(clanWar, ClanWarType.LEAGUE);
        mergeClanWarMember(clanWarEntity, clanWar);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processSyncClanWarMember(ClanWarEntity clanWarEntity, ClanWar clanWar) {
        // 리그전의 경우 라인업에 따라 실제 전투인원이 바뀌기 때문에 수집하면서 데이터 조정해주고 있음
        Map<String, ClanWarMember> clanWarMemberMap = clanWar.getClan()
                                                             .getMembers()
                                                             .stream()
                                                             .collect(Collectors.toMap(ClanWarMember::getTag,
                                                                                       clanWarMemberEntity -> clanWarMemberEntity));

        List<ClanWarMemberEntity> removeMemberEntities = new ArrayList<>();
        for (ClanWarMemberEntity clanWarMemberEntity : clanWarEntity.getMembers()) {
            ClanWarMember clanWarMember = clanWarMemberMap.get(clanWarMemberEntity.getId().getTag());
            if (Objects.isNull(clanWarMember)) {
                removeMemberEntities.add(clanWarMemberEntity);
            }
        }

        if (!removeMemberEntities.isEmpty()) {
            for (ClanWarMemberEntity removeMemberEntity : removeMemberEntities) {
                // remove list
                clanWarEntity.getMembers().removeIf(dbMember -> Objects.equals(dbMember.getId().getTag(), removeMemberEntity.getId().getTag()));

                clanWarMemberRepository.deleteById(removeMemberEntity.getId());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mergeClanWarMember(ClanWarEntity clanWarEntity, ClanWar clanWar) {
        // 이미 수집된 전쟁은 warId를 전달받지 못함
        if (ObjectUtils.isEmpty(clanWarEntity.getWarId())) return;

        if (clanWarEntity.isLeagueWar()) {
            processSyncClanWarMember(clanWarEntity, clanWar);
        }

        Map<String, ClanWarMemberEntity> clanWarMemberEntityMap = clanWarEntity.getMembers()
                                                                               .stream()
                                                                               .collect(Collectors.toMap(clanWarMemberEntity -> clanWarMemberEntity.getId().getTag(),
                                                                                                         clanWarMemberEntity -> clanWarMemberEntity));

        Set<String> clanWarWhitelist = clanWarWhitelistQueryRepository.findAll();
        for (ClanWarMember clanWarMember : clanWar.getClan().getMembers()) {
            ClanWarMemberEntity clanWarMemberEntity = clanWarMemberEntityMap.get(clanWarMember.getTag());
            if (ObjectUtils.isEmpty(clanWarMemberEntity)) {
                clanWarMemberEntity = ClanWarMemberEntity.builder()
                                                         .id(ClanWarMemberPKEntity.builder()
                                                                                  .warId(clanWarEntity.getWarId())
                                                                                  .tag(clanWarMember.getTag())
                                                                                  .build())
                                                         .mapPosition(clanWarMember.getMapPosition())
                                                         .name(clanWarMember.getName())
                                                         .build();

                clanWarEntity.addMember(clanWarMemberEntity);
            }

            if (!clanWarEntity.isLeagueWar()) {
                // 리그전이 아닌 경우 화이트리스트에 등록된 계정은 강참 설정으로 전환을 기본 정책으로 설정
                if (clanWarWhitelist.contains(clanWarMemberEntity.getId().getTag())) {
                    clanWarMemberEntity.changeNecessaryAttack(YnType.N);
                }
            }

            mergeClanWarMemberAttacks(clanWarMemberEntity, clanWarMember);
        }
    }

    private void mergeClanWarMemberAttacks(ClanWarMemberEntity clanWarMemberEntity, ClanWarMember clanWarMember) {
        if (CollectionUtils.isEmpty(clanWarMember.getAttacks())) return;

        Map<Integer, ClanWarMemberAttackEntity> clanWarMemberAttackEntityMap = clanWarMemberEntity.getAttacks()
                                                                                                  .stream()
                                                                                                  .collect(Collectors.toMap(clanWarMemberAttackEntity -> clanWarMemberAttackEntity.getId().getOrder(),
                                                                                                                            clanWarMemberAttackEntity -> clanWarMemberAttackEntity));

        for (ClanWarAttack clanWarAttack : clanWarMember.getAttacks()) {
            ClanWarMemberAttackEntity clanWarMemberAttackEntity = clanWarMemberAttackEntityMap.get(clanWarAttack.getOrder());

            if (!ObjectUtils.isEmpty(clanWarMemberAttackEntity)) { continue; }

            clanWarMemberAttackEntity = ClanWarMemberAttackEntity.builder()
                                                                 .id(ClanWarMemberAttackPKEntity.builder()
                                                                                                .warId(clanWarMemberEntity.getId().getWarId())
                                                                                                .tag(clanWarMemberEntity.getId().getTag())
                                                                                                .order(clanWarAttack.getOrder())
                                                                                                .build())
                                                                 .stars(clanWarAttack.getStars())
                                                                 .destructionPercentage(clanWarAttack.getDestructionPercentage())
                                                                 .duration(clanWarAttack.getDuration())
                                                                 .build();

            clanWarMemberEntity.addAttacks(clanWarMemberAttackEntity);
        }
    }

    private ClanWarEntity saveClanWar(ClanWar clanWar, ClanWarType clanWarType) {
        ClanWarEntity clanWarEntity = ClanWarEntity.builder()
                                                   .warTag(clanWar.getWarTag())
                                                   .state(clanWar.getState())
                                                   .type(clanWarType)
                                                   .battleType(clanWar.getBattleModifier())
                                                   .clanTag(clanWar.getClan().getTag())
                                                   .preparationStartTime(getLocalDateTime(clanWar.getPreparationStartTime()))
                                                   .startTime(getLocalDateTime(clanWar.getStartTime()))
                                                   .endTime(getLocalDateTime(clanWar.getEndTime()))
                                                   .teamSize(clanWar.getTeamSize())
                                                   .attacksPerMember(clanWar.getAttacksPerMember())
                                                   .build();

        return clanWarQueryRepository.save(clanWarEntity);
    }

    private LocalDateTime getLocalDateTime(String preparationStartTime) {
        long milliSec = timeConverter.toEpochMilliSecond(preparationStartTime);
        return timeConverter.toLocalDateTime(milliSec);
    }

    private ClanWar findClanWarFromFile(ClanWarEntity clanWarEntity) {
        final String path = CLAN_WAR_GROUP_DIR.replace("{clanTag}", clanWarEntity.getClanTag());

        // directory: ./clan-war/{clanTag}/{startDate}.json
        String startDate = clanWarEntity.getStartTime().format(DateTimeFormatter.BASIC_ISO_DATE);
        File file = new File(path, makeJsonFileName(startDate));

        // Not Found.
        if (!file.exists()) return null;

        try {
            return objectMapper.readValue(file, ClanWar.class);
        } catch (Exception e) {
            log.error("파일({}) 파싱 오류, {}", file.getAbsoluteFile(), e.getMessage(), e);
            return null;
        }
    }

    private String makeJsonFileName(String warTag) {
        String JSON_FILE_NAME = "%s.json";
        return JSON_FILE_NAME.formatted(warTag);
    }

    @Transactional
    public void collectCurrentClanWar() {
        final String state = ClanWarEntity.STATE_WAR_COLLECTED;
        LocalDateTime now = LocalDateTime.now();
        List<ClanWarEntity> collectClanWars = clanWarQueryRepository.findAllByEndTimeAfterAndStateNot(now, state);

        for (ClanWarEntity clanWarEntity : collectClanWars) {
            collectClanWar(clanWarEntity);
        }
    }

    private void collectClanWar(ClanWarEntity clanWarEntity) {

        ClanWar clanWar = getClanWar(clanWarEntity);

        if (Objects.isNull(clanWar)) return;

        mergeClanWarMember(clanWarEntity, clanWar);
        clanWarEntity.changeStateWarCollected();
    }

    private ClanWar getClanWar(ClanWarEntity clanWarEntity) {
        if (clanWarEntity.isLeagueWar()) {
            return findClanLeagueWar(clanWarEntity);
        }

        return findClanWar(clanWarEntity);
    }

    private ClanWar findClanWar(ClanWarEntity clanWarEntity) {
        ClanWar clanWar = findClanWarFromFile(clanWarEntity);

        if (ObjectUtils.isEmpty(clanWar)) { return clanWar; }
        if (!clanWar.isWarEnded()) {
            // 수집하기전 클랜전 종료 상태 데이터로 수집하도록 데이터 현행화 수행
            Optional<ClanWar> findClanWar = clanApiService.findClanCurrentWarByClanTag(clanWarEntity.getClanTag());
            if (findClanWar.isEmpty()) {
                log.info("클랜전이 종료되었으나 클랜 종료 데이터는 수집하지 못함. {}", clanWarEntity.getClanTag());
            } else {
                // 수집하기 직전 최신 데이터로 수집 진행
                ClanWar currentClanWar = findClanWar.get();
                if (currentClanWar.isWarEnded() || currentClanWar.isInWar()) {
                    // 최신 데이터가 전쟁 종료되었거나 진행중이면 해당 데이터로 수집 진행
                    clanWar = currentClanWar;
                } else {
                    log.info("클랜전 데이터 수집했으나 전쟁 종료 또는 진행중 데이터가 아니기에 서버에 작성된 클랜전 파일 기준으로 수집 진행. {}", clanWarEntity.getClanTag());
                }

                // 최신 데이터 기록
                writeClanWarToFile(clanWar);
            }
        }

        return clanWar;
    }

    private ClanWar findClanLeagueWar(ClanWarEntity clanWarEntity) {
        String clanTag = clanWarEntity.getClanTag();
        String season = clanWarEntity.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String warTag = clanWarEntity.getWarTag();

        ClanWar clanWar = findClanWarLeagueFromFile(clanTag, season, warTag);
        if (ObjectUtils.isEmpty(clanWar)) { return clanWar; }

        if (!clanWar.isWarEnded()) {
            // 수집하기전 클랜전 종료 상태 데이터로 수집하도록 데이터 현행화 수행
            Optional<ClanWar> findClanWarLeague = clanApiService.findWarLeagueByWarTag(warTag);
            if (findClanWarLeague.isEmpty()) {
                log.info("클랜전이 종료되었으나 클랜 종료 데이터는 수집하지 못함. {}", clanTag);
            } else {
                // 수집하기 직전 최신 데이터로 수집 진행
                ClanWar clanWarLeague = findClanWarLeague.get();
                if (clanWarLeague.isWarEnded() || clanWarLeague.isInWar()) {
                    // 최신 데이터가 전쟁 종료되었거나 진행중이면 해당 데이터로 수집 진행
                    clanWar = clanWarLeague;
                } else {
                    log.info("클랜전 데이터 수집했으나 전쟁 종료 또는 진행중 데이터가 아니기에 서버에 작성된 클랜전 파일 기준으로 수집 진행. {}", clanTag);
                }

                // 최신 데이터 기록
                writeClanWarLeagueResult(clanTag, season, warTag, clanWar);
            }
        }

        clanWar.swapWarClan(clanTag);
        return clanWar;
    }

    final String LEAGUE_WAR_ROOT_DIR = "./war-league";
    final String LEAGUE_WAR_SEASON_DIR = "/{season}/{clanTag}";
    final String LEAGUE_WAR_SEASON_INFO_FILE_NAME = "league-info";
    final String LEAGUE_WAR_ROUND_DIR = LEAGUE_WAR_ROOT_DIR + LEAGUE_WAR_SEASON_DIR + "/round";

    final String LEAGUE_WAR_INFO_DIR = LEAGUE_WAR_ROOT_DIR + LEAGUE_WAR_SEASON_DIR;

    private String getLeagueWarSeasonInfoFileName() {
        return makeJsonFileName(LEAGUE_WAR_SEASON_INFO_FILE_NAME);
    }

    public ClanCurrentWarLeagueGroup findClanCurrentWarLeagueGroupFromFile(String season, String clanTag) {
        final String path = LEAGUE_WAR_INFO_DIR.replace("{season}", season)
                                               .replace("{clanTag}", clanTag);

        // directory: ./war-league/{season}/{clanTag}/round/{warTag}.json
        File file = new File(path, getLeagueWarSeasonInfoFileName());

        // Not Found.
        if (!file.exists()) return null;

        try {
            return objectMapper.readValue(file, ClanCurrentWarLeagueGroup.class);
        } catch (Exception e) {
            log.error("파일({}) 파싱 오류, {}", file.getAbsoluteFile(), e.getMessage(), e);
            return null;
        }
    }

    public void writeClanWarLeagueSeasonGroup(String clanTag, ClanCurrentWarLeagueGroup clanCurrentWarLeagueGroup) {
        try {
            // directory: ./war-league/{season}/{clanTag}
            final String path = LEAGUE_WAR_INFO_DIR.replace("{season}", clanCurrentWarLeagueGroup.getSeason())
                                                   .replace("{clanTag}", clanTag);

            ClassPathResource resource = checkAndMakeDirectory(path);

            // 이하 클랜태그별 폴더
            String fileName = getLeagueWarSeasonInfoFileName();

            File file = new File(resource.getPath(), fileName);
            makeEmptyFile(file);

            writeFile(file, clanCurrentWarLeagueGroup);
        } catch (IOException e) {
            log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    public ClanWar findClanWarLeagueFromFile(String clanTag, String season, String warTag) {
        final String path = LEAGUE_WAR_ROUND_DIR.replace("{season}", season)
                                                .replace("{clanTag}", clanTag);

        // directory: ./war-league/{season}/{clanTag}/round/{warTag}.json
        File file = new File(path, makeJsonFileName(warTag));

        // Not Found.
        if (!file.exists()) return null;

        try {
            return objectMapper.readValue(file, ClanWar.class);
        } catch (Exception e) {
            log.error("파일({}) 파싱 오류, {}", warTag, e.getMessage(), e);
            return null;
        }
    }

    public void writeClanWarLeagueResult(String clanTag, String season, String warTag, ClanWar clanWar) {
        try {
            // directory: ./war-league/{season}/{clanTag}/round
            final String path = LEAGUE_WAR_ROUND_DIR.replace("{season}", season)
                                                    .replace("{clanTag}", clanTag);

            ClassPathResource resource = checkAndMakeDirectory(path);

            File file = new File(resource.getPath(), makeJsonFileName(warTag));
            makeEmptyFile(file);

            writeFile(file, clanWar);
        } catch (IOException e) {
            log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private <T> void writeFile(File file, T data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(objectMapper.writeValueAsString(data));
        writer.close();
    }

    private void makeEmptyFile(File file) throws IOException {
        if (file.exists()) return;

        boolean result = file.createNewFile();
        if (!result) {
            throw new IOException("파일 생성 실패: " + file.getAbsolutePath());
        }
    }

    private ClassPathResource checkAndMakeDirectory(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            File directory = new File(resource.getPath());
            directory.mkdirs();
        }
        return resource;
    }

    public void writeClanWarToFile(ClanWar clanWar) {
        try {
            // directory: ./clan-war/{clanTag}
            final String path = CLAN_WAR_GROUP_DIR.replace("{clanTag}", clanWar.getClan().getTag());

            ClassPathResource resource = checkAndMakeDirectory(path);

            long startTimeMilliSec = timeConverter.toEpochMilliSecond(clanWar.getStartTime());
            LocalDate startDate = timeConverter.toLocalDate(startTimeMilliSec);
            String fileName = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);

            File file = new File(resource.getPath(), makeJsonFileName(fileName));
            makeEmptyFile(file);

            writeFile(file, clanWar);
        } catch (IOException e) {
            log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    public List<ClanWarRecordDTO> getRankingClanWarStars(LocalDate searchMonth, String clanTag, String searchType) {
        LocalDateTime fromStartTime = TimeUtils.toFirstDayOfMonthDateTime(searchMonth);
        LocalDateTime toStartTime = TimeUtils.toLastDayOfMonthDateTime(searchMonth);

        Pageable pageable = makePageable(searchType);

        if (StringUtils.isEmpty(clanTag)) {
            return clanWarQueryRepository.findClanWarRecordsByClanWarTypeAndPreparationStartTimePeriod(ClanWarType.NONE, fromStartTime, toStartTime, pageable);
        }

        return clanWarQueryRepository.findClanWarRecordsByClanTagAndClanWarTypeAndPreparationStartTimePeriod(clanTag, ClanWarType.NONE, fromStartTime, toStartTime, pageable);
    }

    private Pageable makePageable(String searchType) {
        // 임시 전체 조회 코드 설정
        if ("ALL".equals(searchType)) {
            return Pageable.unpaged();
        }

        return Pageable.ofSize(hallOfFameConfig.getRanking());
    }

    private LocalDateTime getStartTime(LocalDate searchMonth) {
        LocalDate startDate = searchMonth.with(TemporalAdjusters.firstDayOfMonth());
        return LocalDateTime.of(startDate, LocalDateTime.MIN.toLocalTime());
    }

    private LocalDateTime getEndTime(LocalDate endTime) {
        LocalDate endDate = endTime.with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(endDate, LocalTime.MAX.withNano(999_999_000));
    }

    public List<ClanWarResponse> getClanWars(LocalDate startDate, LocalDate endDate) {

        LocalDateTime fromStartTime = TimeUtils.withMinTime(startDate);
        LocalDateTime toStartTime = TimeUtils.withMaxTime(endDate);

        List<ClanWarEntity> clanWarEntities = clanWarQueryRepository.findAllByStartTimePeriod(fromStartTime, toStartTime);

        return clanWarEntities.stream()
                              .map(entityClanWarResponseConverter::convertWithoutMember)
                              .collect(Collectors.toList());
    }

    public List<ClanWarMissingAttackPlayerDTO> getClanWarMissingAttackPlayers(LocalDate startDate, LocalDate endDate) {
        LocalDateTime fromStartTime = TimeUtils.withMinTime(startDate);
        LocalDateTime toStartTime = TimeUtils.withMaxTime(endDate);

        return clanWarQueryRepository.findAllMissingAttackPlayerByStartTimePeriod(fromStartTime, toStartTime);
    }

    public List<ClanWarMissingAttackPlayerDTO> getClanWarMissingAttackPlayersWithName(String playerName, Integer queryDate) {
        LocalDateTime fromStartTime = TimeUtils.getDateMinTimeDaysAgo(queryDate);
        LocalDateTime toStartTime = TimeUtils.getDateMaxTimeDaysAgo(0);

        return clanWarQueryRepository.findMissingAttackByNameAndStartTimePeriod(playerName, fromStartTime, toStartTime);
    }

    public List<ClanWarMissingAttackPlayerDTO> getClanWarMissingAttackPlayersWithTag(String playerTag, Integer queryDate) {
        LocalDateTime fromStartTime = TimeUtils.getDateMinTimeDaysAgo(queryDate);
        LocalDateTime toStartTime = TimeUtils.getDateMaxTimeDaysAgo(0);

        return clanWarQueryRepository.findMissingAttackByTagAndStartTimePeriod(playerTag, fromStartTime, toStartTime);
    }

    public ClanWarResponse getClanWarDetail(Long warId) {
        ClanWarEntity clanWarEntity = clanWarQueryRepository.findByWarId(warId).orElseThrow(() -> createNotFoundException("클랜전(%s) 조회 실패".formatted(warId)));

        List<ClanWarMemberEntity> clanWarMemberEntities = clanWarMemberQueryRepository.findAllByWarId(clanWarEntity.getWarId());
        clanWarEntity.changeMembers(clanWarMemberEntities);

        return entityClanWarResponseConverter.convertWithMember(clanWarEntity);
    }

    public List<ClanWarRecordDTO> getRankingLeagueClanWarStars(LocalDate searchMonth, String clanTag, String searchType) {
        LocalDateTime fromStartTime = TimeUtils.toFirstDayOfMonthDateTime(searchMonth);
        LocalDateTime toStartTime = TimeUtils.toLastDayOfMonthDateTime(searchMonth);

        List<ClanWarRecordDTO> rankingHallOfFames;
        if (StringUtils.isEmpty(clanTag)) {
            rankingHallOfFames = clanWarQueryRepository.findClanWarRecordsByClanWarTypeAndPreparationStartTimePeriod(ClanWarType.LEAGUE, fromStartTime, toStartTime, Pageable.unpaged());
        } else {
            rankingHallOfFames = clanWarQueryRepository.findClanWarRecordsByClanTagAndClanWarTypeAndPreparationStartTimePeriod(clanTag, ClanWarType.LEAGUE, fromStartTime, toStartTime, Pageable.unpaged());
        }

        Map<String, Long> leagueClanWarRoundsMap = clanWarQueryRepository.findClanWarCountByClanWarTypeAndPreparationStartTimePeriod(ClanWarType.LEAGUE, fromStartTime, toStartTime);;

        if ("ALL".equalsIgnoreCase(searchType)) {
            return rankingHallOfFames;
        }

        // 완파한 클랜원만 제공
        return rankingHallOfFames.stream()
                                 .filter(ranking -> isCompleteStars(ranking, leagueClanWarRoundsMap))
                                 .toList();
    }

    private boolean isCompleteStars(ClanWarRecordDTO ranking, Map<String, Long> clanWarCountMap) {
        Long clanWarCount = clanWarCountMap.get(ranking.clanTag());
        Long completedStarCount = clanWarCount * 3;
        return Objects.equals(ranking.totalStars(), completedStarCount.intValue());
    }

    @Transactional
    public ClanWarMemberResponse updateClanWarMemberAttackNecessaryAttack(Long warId, String playerTag) {

        ClanWarMemberEntity clanWarMemberEntity = clanWarMemberRepository.findById(ClanWarMemberPKEntity.builder()
                                                                                                        .warId(warId)
                                                                                                        .tag(playerTag)
                                                                                                        .build())
                                                                         .orElseThrow(() -> createNotFoundException("클랜전(%s) 계정(%s) 정보 없음".formatted(warId, playerTag)));

        YnType currentNecessaryAttackYn = clanWarMemberEntity.getNecessaryAttackYn();
        YnType toNecessaryAttackYn = Objects.equals(currentNecessaryAttackYn, YnType.Y) ? YnType.N : YnType.Y;
        clanWarMemberEntity.changeNecessaryAttack(toNecessaryAttackYn);

        return entityClanWarMemberResponseConverter.convert(clanWarMemberEntity);
    }

    @Transactional(readOnly = true)
    public List<ClanWarMemberResponse> getClanWarMembers(ClanWarMemberQuery clanWarMemberQuery) {

        ClanWarEntity clanWar = clanWarQueryRepository.findByClanTagAndStartTime(clanWarMemberQuery.getClanTag(), clanWarMemberQuery.getStartTime())
                                                      .orElseThrow(() -> createNotFoundException("클랜전(%s) 시작시간(%s) 정보 없음".formatted(clanWarMemberQuery.getClanTag(), clanWarMemberQuery.getStartTime())));

        List<ClanWarMemberEntity> clanWarMemberEntities = clanWarMemberQueryRepository.findAllByWarId(clanWar.getWarId());

        if (clanWarMemberQuery.isConditionWithNecessaryAttackYn()) {
            clanWarMemberEntities = clanWarMemberEntities.stream()
                                                         .filter(clanWarMember -> Objects.equals(clanWarMember.getNecessaryAttackYn(), clanWarMemberQuery.getNecessaryAttackYn()))
                                                         .toList();
        }

        return clanWarMemberEntities.stream()
                                    .map(entityClanWarMemberResponseConverter::convert)
                                    .toList();
    }
}