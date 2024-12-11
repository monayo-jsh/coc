package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanAssignedPlayerEntity.clanAssignedPlayerEntity;

import com.querydsl.core.BooleanBuilder;
import org.springframework.util.StringUtils;

public class ClanAssignedPlayerQueryBuilder {

    private final String assignedDate;
    private final String clanTag;

    private ClanAssignedPlayerQueryBuilder(String assignedDate, String clanTag) {
        if (!StringUtils.hasText(assignedDate)) {
            throw new IllegalArgumentException("assignedDate can not be null");
        }

        this.assignedDate = assignedDate;
        this.clanTag = clanTag;
    }

    private boolean isClanTagSearch() {
        if (!StringUtils.hasText(clanTag)) {
            return false;
        }

        // 전체 클랜 대상
        if ("all".equals(clanTag)) {
            return false;
        }

        return true;
    }

    public BooleanBuilder build() {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanAssignedPlayerEntity.id.seasonDate.eq(assignedDate));

        if (isClanTagSearch()) {
            condition.and(clanAssignedPlayerEntity.clan.tag.eq(clanTag));
        }

        return condition;
    }

    public static ClanAssignedPlayerQueryBuilder create(String assignedDate, String clanTag) {
        return new ClanAssignedPlayerQueryBuilder(assignedDate, clanTag);
    }
}
