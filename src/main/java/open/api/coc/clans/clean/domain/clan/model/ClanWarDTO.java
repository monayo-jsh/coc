package open.api.coc.clans.clean.domain.clan.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
public class ClanWarDTO {

    private final Long warId;
    private final String clanName;
    private final String state;
    private final ClanWarType type;
    private final String battleType;
    private final LocalDateTime preparationStartTime;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Integer teamSize;
    private final Integer attacksPerMember;
    private final String warTag;
    private final List<ClanWarParticipantDTO> members;

    public ClanWarDTO(Long warId, String clanName, String state, ClanWarType type,
                      String battleType,
                      LocalDateTime preparationStartTime, LocalDateTime startTime,
                      LocalDateTime endTime, Integer teamSize, Integer attacksPerMember,
                      String warTag) {
        this.warId = warId;
        this.clanName = clanName;
        this.state = state;
        this.type = type;
        this.battleType = battleType;
        this.preparationStartTime = preparationStartTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teamSize = teamSize;
        this.attacksPerMember = attacksPerMember;
        this.warTag = warTag;
        this.members = new ArrayList<>();
    }

    public List<ClanWarParticipantDTO> getNecessaryAttackParticipants(YnType necessaryAttackYn) {
        if (Objects.isNull(necessaryAttackYn)) return members;

        if (YnType.Y.equals(necessaryAttackYn)) {
            return members.stream().filter(ClanWarParticipantDTO::isNecessaryAttack).toList();
        }

        return members.stream().filter(ClanWarParticipantDTO::isUnNecessaryAttack).toList();
    }

    public void changeMembers(List<ClanWarParticipantDTO> members) {
        this.members.clear();
        this.members.addAll(members);
    }
}
