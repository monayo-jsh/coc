package open.api.coc.clans.clean.application.raid.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import open.api.coc.clans.clean.application.raid.dto.RaidScorePlayerNameQuery;
import open.api.coc.clans.clean.application.raid.dto.RaidScorePlayerTagQuery;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;

public class RaidScoreQueryFactory {

    public static RaidScoreQuery create(String playerTag, String playerName) {

        Map<Boolean, Supplier<RaidScoreQuery>> queryMap = new HashMap<>();
        queryMap.put(StringUtils.hasText(playerTag), () -> RaidScorePlayerTagQuery.create(playerTag));
        queryMap.put(StringUtils.hasText(playerName), () -> RaidScorePlayerNameQuery.create(playerName));

        return queryMap.entrySet()
                       .stream()
                       .filter(Map.Entry::getKey)
                       .map(Map.Entry::getValue)
                       .findFirst()
                       .orElseThrow(() -> BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "playerTag 또는 playerName를 입력해주세요."))
                       .get();
    }

}