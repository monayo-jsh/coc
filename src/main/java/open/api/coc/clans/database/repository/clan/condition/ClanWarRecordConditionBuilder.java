package open.api.coc.clans.database.repository.clan.condition;

import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.core.BooleanBuilder;
import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;

public class ClanWarRecordConditionBuilder {
    private final BooleanBuilder builder;

    public ClanWarRecordConditionBuilder(ClanWarType warType, LocalDateTime from, LocalDateTime to) {
        if (warType == null) {
            throw new IllegalArgumentException("ClanWarType must not be null");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Date parameters must not be null");
        }

        builder = new BooleanBuilder();
        builder.and(clanWarEntity.type.eq(warType))
               .and(clanWarEntity.preparationStartTime.between(from, to))
               //전쟁 준비중은 집계 대상에서 제외
               .and(clanWarEntity.state.ne("preparation"));
    }

    public ClanWarRecordConditionBuilder withClanTag(String clanTag) {
        if (clanTag == null || clanTag.trim().isEmpty()) {
            throw new IllegalArgumentException("clanTag must not be null or empty");
        }

        builder.and(clanWarEntity.clanTag.eq(clanTag));
        return this;
    }

    public BooleanBuilder build() {
        return builder;
    }
}
