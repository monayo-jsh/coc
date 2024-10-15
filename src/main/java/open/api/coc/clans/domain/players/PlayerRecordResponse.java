package open.api.coc.clans.domain.players;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PlayerRecordResponse(

    String tag,
    String name,

    Integer oldTrophies,
    Integer newTrophies,
    Integer diffTrophies,

    Integer oldAttackWins,
    Integer newAttackWins,

    Integer oldDefenceWins,
    Integer newDefenceWins,

    LocalDateTime recordedAt,

    LocalDate baseDate

) {

    public PlayerRecordResponse(String tag, String name,
                                Integer oldTrophies, Integer newTrophies,
                                Integer oldAttackWins, Integer newAttackWins,
                                Integer oldDefenceWins, Integer newDefenceWins,
                                LocalDateTime recordedAt) {
        this(tag, name,
             oldTrophies, newTrophies, newTrophies - oldTrophies,
             oldAttackWins, newAttackWins,
             oldDefenceWins, newDefenceWins,
             recordedAt,
             getBaseDate(recordedAt));
    }

    private static LocalDate getBaseDate(LocalDateTime recordedAt) {
        if (recordedAt.getHour() < 14) {
            return recordedAt.minusDays(1).toLocalDate();
        }

        return recordedAt.toLocalDate();
    }
}
