package open.api.coc.clans.domain.clans.query;

import static open.api.coc.clans.database.entity.clan.QClanContentEntity.clanContentEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import open.api.coc.clans.database.entity.common.YnType;

public enum ClanWarType {
    normal {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanWarYn.eq(YnType.Y.name());
        }
    },
    parallel {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanWarParallelYn.eq(YnType.Y.name());
        }
    },
    league {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.warLeagueYn.eq(YnType.Y.name());
        }
    };

    public abstract BooleanExpression getCondition();
}
