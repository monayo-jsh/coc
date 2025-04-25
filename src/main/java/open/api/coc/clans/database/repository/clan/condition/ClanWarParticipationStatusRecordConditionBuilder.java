package open.api.coc.clans.database.repository.clan.condition;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.core.BooleanBuilder;
import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;

public class ClanWarParticipationStatusRecordConditionBuilder {
    private final BooleanBuilder builder;

    public ClanWarParticipationStatusRecordConditionBuilder(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Date parameters must not be null");
        }

        builder = new BooleanBuilder();
        builder.and(clanWarEntity.preparationStartTime.between(from, to));
    }

    public ClanWarParticipationStatusRecordConditionBuilder withPlayerTag(String playerTag) {
        if (playerTag == null || playerTag.trim().isEmpty()) {
            throw new IllegalArgumentException("playerTag must not be null or empty");
        }

        builder.and(playerEntity.playerTag.eq(playerTag));
        return this;
    }

    public ClanWarParticipationStatusRecordConditionBuilder withPlayerName(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("playerName must not be null or empty");
        }

        builder.and(playerEntity.name.like(playerName + "%"));
        return this;
    }

    public BooleanBuilder build() {
        return builder;
    }
}
