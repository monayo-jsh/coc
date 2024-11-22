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

    @Override
    public Clan findByTag(String clanTag) {
        try {
            ClanResponse clanResponse = restClient.get()
                                                  .uri(clashOfClanConfig.getClansClanTagUri(), clanTag)
                                                  .retrieve()
                                                  .body(ClanResponse.class);

            return clanResponseMapper.toClan(clanResponse);
        } catch (Exception e) {
            throw ClanClientException.ofClan(clanTag);
        }
    }

    @Override
    public List<ClanMember> findMembersByTag(String clanTag) {
        try {
            ClanMemberListResponse clanResponse = restClient.get()
                                                            .uri(clashOfClanConfig.getClansClanMembersUri(), clanTag)
                                                            .retrieve()
                                                            .body(ClanMemberListResponse.class);

            if (clanResponse == null) {
                throw ClanClientException.ofClanMember(clanTag);
            }

            return clanResponse.getItems()
                               .stream()
                               .map(clanResponseMapper::toClanMember)
                               .collect(Collectors.toList());

        } catch (Exception e) {
            throw ClanClientException.ofClanMember(clanTag);
        }
    }

}
