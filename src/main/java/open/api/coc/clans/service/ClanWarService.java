package open.api.coc.clans.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackPKEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberPKEntity;
import open.api.coc.clans.database.repository.clan.ClanWarRespository;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.clan.ClanWarAttack;
import open.api.coc.external.coc.clan.domain.clan.ClanWarMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanWarService {

    private final ClanWarRespository clanWarRespository;

    private final TimeConverter timeConverter;

    private final String CLAN_WAR_ROOT_DIR = "./clan-war";
    private final String CLAN_WAR_GROUP_DIR = CLAN_WAR_ROOT_DIR + "/{clanTag}";
    private final String JSON_FILE_NAME = "%s.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
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

        return clanWarRespository.save(clanWarEntity);
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
