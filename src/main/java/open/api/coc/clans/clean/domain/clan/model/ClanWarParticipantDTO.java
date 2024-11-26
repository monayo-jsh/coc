package open.api.coc.clans.clean.domain.clan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
public class ClanWarParticipantDTO {

    private final Long warId;
    private final String tag;

    private final String name;
    private final Integer mapPosition;
    private final YnType necessaryAttackYn;

    private final List<ClanWarParticipantAttackDTO> attacks;

    public ClanWarParticipantDTO(Long warId, String tag, String name, Integer mapPosition,
                                 YnType necessaryAttackYn, List<ClanWarParticipantAttackDTO> attacks) {
        this.warId = warId;
        this.tag = tag;
        this.name = name;
        this.mapPosition = mapPosition;
        this.necessaryAttackYn = necessaryAttackYn;
        this.attacks = makeAttacks(attacks);
    }

    private ArrayList<ClanWarParticipantAttackDTO> makeAttacks(List<ClanWarParticipantAttackDTO> attacks) {
        if(attacks == null) {
            return new ArrayList<>();
        }

        List<ClanWarParticipantAttackDTO> validAttacks = attacks.stream()
                                                                .filter(attack -> Objects.nonNull(attack.getWarId()))
                                                                .toList();
        return new ArrayList<>(validAttacks);
    }

    public void addAttacks(List<ClanWarParticipantAttackDTO> attacks) {
        this.attacks.addAll(attacks);
    }

    public boolean isNecessaryAttack() {
        return YnType.Y.equals(necessaryAttackYn);
    }

    public boolean isUnNecessaryAttack() {
        return !isNecessaryAttack();
    }


}
