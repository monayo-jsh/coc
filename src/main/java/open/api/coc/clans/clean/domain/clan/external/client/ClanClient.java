package open.api.coc.clans.clean.domain.clan.external.client;

import open.api.coc.clans.clean.domain.clan.model.Clan;

public interface ClanClient {

    Clan findByTag(String clanTag);

}