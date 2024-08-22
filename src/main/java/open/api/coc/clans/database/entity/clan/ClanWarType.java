package open.api.coc.clans.database.entity.clan;

import static open.api.coc.clans.database.entity.clan.QClanContentEntity.clanContentEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import open.api.coc.clans.database.entity.common.YnType;

public enum ClanWarType {

    NONE {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanWarYn.eq(YnType.Y.name());
        }
    },

    PARALLEL {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanWarParallelYn.eq(YnType.Y.name());
        }
    },

    LEAGUE {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.warLeagueYn.eq(YnType.Y.name());
        }
    };

    public abstract BooleanExpression getCondition();

}
