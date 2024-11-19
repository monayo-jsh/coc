package open.api.coc.clans.clean.application.clan.dto;

import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMissingAttackSearchCriteria;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;

public record ClanWarMissingAttackPlayerQuery(

    String tag,
    String name,
    Integer queryDate

) {

    public static ClanWarMissingAttackPlayerQuery create(String tag, String name, Integer queryDate) {
        ClanWarMissingAttackPlayerQuery query = new ClanWarMissingAttackPlayerQuery(tag, name, queryDate);
        query.validate();
        return query;
    }

    private void validate() {
        if (StringUtils.hasText(tag)) return;
        if (StringUtils.hasText(name)) return;

        throw new BadRequestException("검색 조건으로 '태그' 또는 '이름' 중 하나를 반드시 입력해야 합니다.");
    }

    public ClanWarMissingAttackSearchCriteria toSearchCriteria() {
        return ClanWarMissingAttackSearchCriteria.create(tag, name, queryDate);
    }
}
