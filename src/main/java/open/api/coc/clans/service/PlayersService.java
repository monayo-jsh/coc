package open.api.coc.clans.service;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.domain.players.converter.PlayerResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayersService {

    private final ClanApiService clanApiService;
    private final PlayerResponseConverter playerResponseConverter;

    public PlayerResponse findPlayerBy(String playerTag) {
        Player player = clanApiService.findPlayerBy(playerTag)
                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "플레이어 조회 실패"));

        return playerResponseConverter.convert(player);

    }
}
