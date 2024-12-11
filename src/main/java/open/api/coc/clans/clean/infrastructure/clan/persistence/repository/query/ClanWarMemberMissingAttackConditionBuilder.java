package open.api.coc.clans.clean.infrastructure.clan.persistence.repository.query;

import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.BooleanBuilder;
import java.time.LocalDateTime;

public class ClanWarMemberMissingAttackConditionBuilder {
    private final BooleanBuilder builder;

    public ClanWarMemberMissingAttackConditionBuilder(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Date parameters must not be null");
        }

        builder = new BooleanBuilder();
        builder.and(clanWarEntity.state.ne("preparation"))
               .and(clanWarEntity.startTime.between(from, to));
    }

    public ClanWarMemberMissingAttackConditionBuilder withPlayerName(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("playerName must not be null or empty");
        }

        builder.and(clanWarMemberEntity.name.startsWith(playerName));
        return this;
    }

    public ClanWarMemberMissingAttackConditionBuilder withPlayerTag(String playerTag) {
        if (playerTag == null || playerTag.trim().isEmpty()) {
            throw new IllegalArgumentException("playerTag must not be null or empty");
        }

        builder.and(clanWarMemberEntity.id.tag.eq(playerTag));
        return this;
    }

    public BooleanBuilder build() {
        return builder;
    }
}
