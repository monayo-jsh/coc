package open.api.coc.clans.domain.clans.query;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.clans.common.exception.handler.ExceptionHandler;
import open.api.coc.clans.database.entity.clan.ClanWarType;

@Getter
public class WarClanQuery {

    private final ClanWarType type;

    private WarClanQuery(ClanWarType type) {
        this.type = type;
    }

    public static WarClanQuery create(String type) throws BadRequestException {
        try {
            ClanWarType clanWarType = ClanWarType.valueOf(type.toUpperCase());
            return new WarClanQuery(clanWarType);
        } catch (Exception e) {
            throw ExceptionHandler.createBadRequestException(
                ExceptionCode.INVALID_PARAMETER,
                "전쟁 유형 %s 중 일치하지 않음. %s".formatted(Arrays.stream(ClanWarType.values()).toList(), type)
            );
        }
    }

    public boolean isLeagueWar() {
        return Objects.equals(ClanWarType.LEAGUE, type);
    }
}
