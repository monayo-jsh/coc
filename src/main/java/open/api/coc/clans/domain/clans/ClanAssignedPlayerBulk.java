package open.api.coc.clans.domain.clans;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.throwBadRequestException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import org.springframework.util.ObjectUtils;

@Getter
public class ClanAssignedPlayerBulk {

    private final String seasonDate;
    private final List<ClanAssignedPlayer> players;

    private ClanAssignedPlayerBulk(String seasonDate, List<ClanAssignedPlayer> players) {
        this.seasonDate = seasonDate;
        this.players = players;
    }

    public static ClanAssignedPlayerBulk create(ClanAssignedPlayerBulkRequest request) {
        ClanAssignedPlayerBulk clanAssignedPlayerBulk = new ClanAssignedPlayerBulk(request.getSeasonDate(), request.toClanAssignedPlayers());
        clanAssignedPlayerBulk.validate();
        return clanAssignedPlayerBulk;
    }

    private void validate() {
        if (isEmpty(this.seasonDate)) throwBadRequestException(ExceptionCode.INVALID_PARAMETER, "배정월 미입력");
        if (isEmpty(this.players)) throwBadRequestException(ExceptionCode.INVALID_PARAMETER, "클랜 배정 정보 미입력");

        validatePlayers();
    }

    private void validatePlayers() {
        Set<String> duplicateClanAssignedPlayers = players.stream()
                                                          .map(player -> {
                                                              if (isEmpty(player.getPlayerTag())) {
                                                                  throwBadRequestException(ExceptionCode.INVALID_PARAMETER, "클랜원태그 미입력");
                                                              }
                                                              if (isEmpty(player.getClanTag())) {
                                                                  throwBadRequestException(ExceptionCode.INVALID_PARAMETER, "배정클랜태그 미입력");
                                                              }
                                                              return player.getPlayerTag();
                                                          })
                                                          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                          .entrySet()
                                                          .stream()
                                                          .filter(m -> m.getValue() > 1)
                                                          .map(Map.Entry::getKey)
                                                          .collect(Collectors.toSet());

        if (duplicateClanAssignedPlayers.isEmpty()) { return; }

        throwBadRequestException(ExceptionCode.INVALID_PARAMETER, "클랜원 중복 배정\n%s".formatted(duplicateClanAssignedPlayers));
    }

    private boolean isEmpty(Object value) {
        return ObjectUtils.isEmpty(value);
    }
}
