package open.api.coc.clans.domain.raid.query;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import org.springframework.util.StringUtils;

public class RaiderScoreQueryFactory {

    public static RaiderScoreQuery create(String playerTag, String playerName) {

        Map<Boolean, Supplier<RaiderScoreQuery>> queryMap = new HashMap<>();
        queryMap.put(StringUtils.hasText(playerTag), () -> RaiderScorePlayerTagQuery.create(playerTag));
        queryMap.put(StringUtils.hasText(playerName), () -> RaiderScorePlayerNameQuery.create(playerName));

        return queryMap.entrySet()
                       .stream()
                       .filter(Map.Entry::getKey)
                       .map(Map.Entry::getValue)
                       .findFirst()
                       .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.INVALID_PARAMETER, "playerTag 또는 playerName를 입력해주세요."))
                       .get();
    }

}