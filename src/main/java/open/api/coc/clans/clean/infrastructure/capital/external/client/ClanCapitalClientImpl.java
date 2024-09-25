package open.api.coc.clans.clean.infrastructure.capital.external.client;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.infrastructure.capital.external.dto.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.clean.infrastructure.capital.external.dto.ClanCapitalRaidSeasonsResponse;
import open.api.coc.clans.clean.infrastructure.capital.external.exception.ClanCapitalRaidSeasonException;
import open.api.coc.clans.clean.infrastructure.capital.external.mapper.ClanCapitalRaidSeasonMapper;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ClanCapitalClientImpl implements ClanCapitalClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;

    private final ClanCapitalRaidSeasonMapper clanCapitalRaidSeasonMapper;

    @Override
    public ClanCapitalRaidSeason findCurrentSeasonByClanTag(String clanTag) {
        final int SEARCH_LIMIT = 1;
        String uri = clashOfClanConfig.getClansClanTagCapitalRaidSeasonsUri() + "?limit=" + SEARCH_LIMIT;

        ClanCapitalRaidSeasonsResponse clanCapitalRaidSeasons = restClient.get()
                                                                          .uri(uri, clanTag)
                                                                          .retrieve()
                                                                          .body(ClanCapitalRaidSeasonsResponse.class);

        if (clanCapitalRaidSeasons == null || clanCapitalRaidSeasons.isEmpty()) {
            throw new ClanCapitalRaidSeasonException(clanTag);
        }

        ClanCapitalRaidSeasonResponse clanCapitalRaidSeasonResponse = clanCapitalRaidSeasons.getItemWithMembers();
        return clanCapitalRaidSeasonMapper.toDomain(clanCapitalRaidSeasonResponse);
    }

}
