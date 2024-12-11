package open.api.coc.clans.clean.domain.player.external.client;

import open.api.coc.clans.clean.domain.player.model.Player;

public interface PlayerClient {

    Player findByTag(String playerTag);

}