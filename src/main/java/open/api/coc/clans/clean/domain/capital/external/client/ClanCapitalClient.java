package open.api.coc.clans.clean.domain.capital.external.client;

import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;

public interface ClanCapitalClient {

    ClanCapitalRaidSeason findCurrentSeasonByClanTag(String clanTag);

}
