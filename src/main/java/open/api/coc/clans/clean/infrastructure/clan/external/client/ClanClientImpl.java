package open.api.coc.clans.clean.infrastructure.clan.external.client;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanMember;
import open.api.coc.clans.clean.infrastructure.clan.external.dto.ClanMemberListResponse;
import open.api.coc.clans.clean.infrastructure.clan.external.dto.ClanResponse;
import open.api.coc.clans.clean.infrastructure.clan.external.exception.ClanClientException;
import open.api.coc.clans.clean.infrastructure.clan.external.mapper.ClanClientResponseMapper;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClanClientImpl implements ClanClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;

    private final ClanClientResponseMapper clanResponseMapper;

    public String makeRequestClanTag(String clanTag) {
        if (clanTag.startsWith("#")) {
            return clanTag;
        }

        return "#" + clanTag;
    }

    @Override
    public Clan findByTag(String clanTag) {
        String requestClanTag = makeRequestClanTag(clanTag);

        try {
            ClanResponse clanResponse = restClient.get()
                                                  .uri(clashOfClanConfig.getClansClanTagUri(), requestClanTag)
                                                  .retrieve()
                                                  .body(ClanResponse.class);

            return clanResponseMapper.toClan(clanResponse);
        } catch (Exception e) {
            throw ClanClientException.ofClan(requestClanTag);
        }
    }

    @Override
    public List<ClanMember> findMembersByTag(String clanTag) {
        String requestClanTag = makeRequestClanTag(clanTag);

        try {
            ClanMemberListResponse clanResponse = restClient.get()
                                                            .uri(clashOfClanConfig.getClansClanMembersUri(), requestClanTag)
                                                            .retrieve()
                                                            .body(ClanMemberListResponse.class);

            if (clanResponse == null) {
                throw ClanClientException.ofClanMember(requestClanTag);
            }

            return clanResponse.getItems()
                               .stream()
                               .map(clanResponseMapper::toClanMember)
                               .collect(Collectors.toList());
        } catch (Exception e) {
            throw ClanClientException.ofClanMember(requestClanTag);
        }
    }

}
