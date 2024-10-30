package open.api.coc.clans.clean.presentation.player.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PlayerLegendRecordResponse {

    private final String tag;
    private final String name;

    private final Integer oldTrophies;
    private final Integer newTrophies;
    private final Integer diffTrophies;

    private final Integer oldAttackWins;
    private final Integer newAttackWins;

    private final Integer oldDefenceWins;
    private final Integer newDefenceWins;

    private final LocalDateTime recordedAt; // 기록일시

    private final LocalDate baseDate; // 리그일

    public PlayerLegendRecordResponse(String tag, String name,
                                      Integer oldTrophies, Integer newTrophies,
                                      Integer oldAttackWins, Integer newAttackWins,
                                      Integer oldDefenceWins, Integer newDefenceWins,
                                      LocalDateTime recordedAt) {

        this.tag = tag;
        this.name = name;

        this.oldTrophies = oldTrophies;
        this.newTrophies = newTrophies;
        this.diffTrophies = newTrophies - oldTrophies;

        this.oldAttackWins = oldAttackWins;
        this.newAttackWins = newAttackWins;

        this.oldDefenceWins = oldDefenceWins;
        this.newDefenceWins = newDefenceWins;

        this.recordedAt = recordedAt;
        this.baseDate = getBaseDate(recordedAt);
    }

    private LocalDate getBaseDate(LocalDateTime recordedAt) {
        if (recordedAt.getHour() < 14) {
            return recordedAt.minusDays(1).toLocalDate();
        }

        return recordedAt.toLocalDate();
    }
}
