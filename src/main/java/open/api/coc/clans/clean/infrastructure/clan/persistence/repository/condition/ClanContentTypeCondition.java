package open.api.coc.clans.clean.infrastructure.clan.persistence.repository.condition;

import static open.api.coc.clans.database.entity.clan.QClanContentEntity.clanContentEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import open.api.coc.clans.database.entity.common.YnType;

public enum ClanContentTypeCondition {

    CLAN_WAR {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanWarYn.eq(YnType.Y.name());
        }
    },
    CLAN_WAR_LEAGUE {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.warLeagueYn.eq(YnType.Y.name());
        }
    },
    CLAN_WAR_PARALLEL {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanWarParallelYn.eq(YnType.Y.name());
        }
    },
    CLAN_CAPITAL {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanCapitalYn.eq(YnType.Y.name());
        }
    },
    CLAN_COMPETITION {
        @Override
        public BooleanExpression getCondition() {
            return clanContentEntity.clanCompetitionYn.eq(YnType.Y.name());
        }
    };


    public abstract BooleanExpression getCondition();

}
