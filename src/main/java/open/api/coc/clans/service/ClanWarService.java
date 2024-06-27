package open.api.coc.clans.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackPKEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberPKEntity;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.clan.ClanWarRepository;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.clan.ClanWarAttack;
import open.api.coc.external.coc.clan.domain.clan.ClanWarMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanWarService {

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
            clanWarEntity.setState(clanWar.getState());
            LocalDateTime endTime = getLocalDateTime(clanWar.getEndTime());
            clanWarEntity.setEndTime(endTime);

            return;
        }

        // 클랜전 기록 생성
        saveClanWar(clanWar);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveClanWarResult() {

        String tag = "#2GJGRU920";
        String startDate = "20240625";

        ClanWar clanWar = findClanWarFromFile(tag, startDate);
        if (ObjectUtils.isEmpty(clanWar)) {
            return;
        }

        ClanWarEntity clanWarEntity = saveClanWar(clanWar);
        saveClanWarMember(clanWarEntity, clanWar);
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

    private ClanWar findClanWarFromFile(String tag, String startDate) {
        final String path = CLAN_WAR_GROUP_DIR.replace("{clanTag}", tag);

        // directory: ./clan-war/{clanTag}/{startDate}.json
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
}
