package open.api.coc.clans.clean.infrastructure.clan.external.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.infrastructure.clan.external.dto.ClanResponse;
import open.api.coc.clans.clean.infrastructure.clan.external.exception.ClanClientException;
import open.api.coc.clans.clean.infrastructure.clan.external.mapper.ClanResponseMapper;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClanClientImpl implements ClanClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;

    private final ClanResponseMapper clanResponseMapper;

    @Override
    public Clan findByTag(String clanTag) {
        try {
            ClanResponse clanResponse = restClient.get()
                                                  .uri(clashOfClanConfig.getClansClanTagUri(), clanTag)
                                                  .retrieve()
                                                  .body(ClanResponse.class);

            return clanResponseMapper.toClan(clanResponse);
        } catch (Exception e) {
            log.error("클랜 연동 요청 실패: %s, response: %s".formatted(clanTag, e.getMessage()));
            throw new ClanClientException(clanTag);
        }
    }

}
