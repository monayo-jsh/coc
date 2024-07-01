package open.api.coc.clans.service;

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
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.config.HallOfFameConfig;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackPKEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberPKEntity;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.clan.ClanWarRepository;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.clan.ClanWarAttack;
import open.api.coc.external.coc.clan.domain.clan.ClanWarMember;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
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
    private final ClanWarRepository clanWarRepository;

    private final TimeConverter timeConverter;

    private final String CLAN_WAR_ROOT_DIR = "./clan-war";
    private final String CLAN_WAR_GROUP_DIR = CLAN_WAR_ROOT_DIR + "/{clanTag}";
    private final String JSON_FILE_NAME = "%s.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 클랜전 현황 메타 정보를 생성
     * 생성된 경우 상태값, 종료시간 갱신
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mergeClanWar(ClanWar clanWar) {

        String clanTag = clanWar.getClan().getTag();

        Optional<ClanEntity> findClan = clanRepository.findById(clanTag);
        if (findClan.isEmpty()) { return; }
        ClanEntity clanEntity = findClan.get();
        if (clanEntity.isNotUsed()) { return; }

        LocalDateTime startTime = getLocalDateTime(clanWar.getStartTime());

        Optional<ClanWarEntity> findClanWar = clanWarRepository.findByClanTagAndStartTime(clanTag, startTime);
        if (findClanWar.isPresent()) {
            ClanWarEntity clanWarEntity = findClanWar.get();

            // 이미 수집된 경우
            if (clanWarEntity.isCollected()) { return; }

            clanWarEntity.setState(clanWar.getState());
            LocalDateTime endTime = getLocalDateTime(clanWar.getEndTime());
            clanWarEntity.setEndTime(endTime);

            return;
        }

        // 클랜전 기록 생성
        saveClanWar(clanWar);
    }

    private void saveClanWarMember(ClanWarEntity clanWarEntity, ClanWar clanWar) {
        for (ClanWarMember clanWarMember : clanWar.getClan().getMembers()) {
            ClanWarMemberEntity clanWarMemberEntity = ClanWarMemberEntity.builder()
                                                                         .id(ClanWarMemberPKEntity.builder()
                                                                                                  .warId(clanWarEntity.getWarId())
                                                                                                  .tag(clanWarMember.getTag())
                                                                                                  .build())
                                                                         .mapPosition(clanWarMember.getMapPosition())
                                                                         .name(clanWarMember.getName())
                                                                         .build();

            saveClanWarMemberAttacks(clanWarMemberEntity, clanWarMember);

            clanWarEntity.addMember(clanWarMemberEntity);
        }
    }

    private void saveClanWarMemberAttacks(ClanWarMemberEntity clanWarMemberEntity, ClanWarMember clanWarMember) {
        if (CollectionUtils.isEmpty(clanWarMember.getAttacks())) return;

        for (ClanWarAttack clanWarAttack : clanWarMember.getAttacks()) {
            ClanWarMemberAttackEntity clanWarMemberAttackEntity = ClanWarMemberAttackEntity.builder()
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

    private ClanWarEntity saveClanWar(ClanWar clanWar) {
        ClanWarEntity clanWarEntity = ClanWarEntity.builder()
                                                   .state(clanWar.getState())
                                                   .battleType(clanWar.getBattleModifier())
                                                   .clanTag(clanWar.getClan().getTag())
                                                   .preparationStartTime(getLocalDateTime(clanWar.getPreparationStartTime()))
                                                   .startTime(getLocalDateTime(clanWar.getStartTime()))
                                                   .endTime(getLocalDateTime(clanWar.getEndTime()))
                                                   .teamSize(clanWar.getTeamSize())
                                                   .attacksPerMember(clanWar.getAttacksPerMember())
                                                   .build();

        return clanWarRepository.save(clanWarEntity);
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
        return JSON_FILE_NAME.formatted(warTag);
    }

    @Transactional
    public void collectCurrentClanWar() {
        final String state = ClanWarEntity.STATE_WAR_COLLECTED;
        LocalDateTime now = LocalDateTime.now();
        List<ClanWarEntity> collectClanWars = clanWarRepository.findAfterEndTimeAndNotState(now, state);

        for (ClanWarEntity clanWarEntity : collectClanWars) {
            collectClanWar(clanWarEntity);
        }
    }

    private void collectClanWar(ClanWarEntity clanWarEntity) {

        ClanWar clanWar = findClanWarFromFile(clanWarEntity);

        if (ObjectUtils.isEmpty(clanWar)) { return; }
        if (!clanWar.isWarEnded()) {
            Optional<ClanWar> findClanWar = clanApiService.findClanCurrentWarByClanTag(clanWarEntity.getClanTag());
            if (findClanWar.isEmpty()) {
                log.info("clanApiService.findClanCurrentWarByClanTag({}) failed ..", clanWarEntity.getClanTag());
                return;
            }
            clanWar = findClanWar.get();
            if (!clanWar.isWarEnded()) { return; }
        }

        saveClanWarMember(clanWarEntity, clanWar);
        clanWarEntity.changeStateWarCollected();
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

    @Transactional
    public void saveCurrentClanWar(ClanWar clanWar) {
        writeClanWarToFile(clanWar);
        mergeClanWar(clanWar);
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

    public List<RankingHallOfFame> getRankingClanWarStars(LocalDate searchMonth, String clanTag) {
        LocalDateTime startTime = getStartTime(searchMonth);
        LocalDateTime endTime = getEndTime(searchMonth);

        if (StringUtils.isEmpty(clanTag)) {
            return clanWarRepository.selectRankingClanWarStars(startTime, endTime, PageRequest.of(0, hallOfFameConfig.getRanking()));
        }

        return clanWarRepository.selectRankingClanWarStarsByClanTag(startTime, endTime, clanTag, PageRequest.of(0, hallOfFameConfig.getRanking()));
    }

    private LocalDateTime getStartTime(LocalDate searchMonth) {
        LocalDate startDate = searchMonth.with(TemporalAdjusters.firstDayOfMonth());
        return LocalDateTime.of(startDate, LocalDateTime.MIN.toLocalTime());
    }

    private LocalDateTime getEndTime(LocalDate endTime) {
        LocalDate endDate = endTime.with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(endDate, LocalTime.MAX.withNano(999_999_000));
    }
}
