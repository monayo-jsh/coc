package open.api.coc.clans.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.Clan;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.domain.ClanCapitalAttackerRes;
import open.api.coc.clans.domain.ClanCapitalUnderAttackerRes;
import open.api.coc.clans.domain.ClanCurrentWarRes;
import open.api.coc.clans.domain.ClanMemberListRes;
import open.api.coc.clans.domain.ClanRes;
import open.api.coc.clans.domain.converter.ClanCapitalRaidSeasonMemberResConverter;
import open.api.coc.clans.domain.converter.ClanCurrentWarResConverter;
import open.api.coc.clans.domain.converter.ClanMemberListResConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeason;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasonMember;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.ClanMemberList;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClanApiService clanApiService;

    private final ClanCapitalRaidSeasonMemberResConverter clanCapitalRaidSeasonMemberResConverter;
    private final ClanCurrentWarResConverter clanCurrentWarResConverter;
    private final ClanMemberListResConverter clanMemberListResConverter;

    public Map<String, Object> findClanByClanTag(String clanTag) {
        return clanApiService.findClanByClanTag(clanTag);
    }

    private List<ClanCapitalRaidSeasonMember> getClanCapitalRaidSeasonsMembers(String clanTag, int limit) {
        ClanCapitalRaidSeasons clanCapitalRaidSeasons = clanApiService.findClanCapitalRaidSeasonsByClanTagAndLimit(clanTag, limit)
                                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜캐피탈 조회 실패"));

        if (clanCapitalRaidSeasons.isEmpty()) {
            return Collections.emptyList();
        }

        ClanCapitalRaidSeason clanCapitalRaidSeason = clanCapitalRaidSeasons.getItemWithMembers();

        return clanCapitalRaidSeason.getMembers();
    }

    public ClanCapitalAttackerRes findClanCapitalRaidSeasons(String clanTag) {
        final int SEARCH_LIMIT = 1;
        List<ClanCapitalRaidSeasonMember> members = getClanCapitalRaidSeasonsMembers(clanTag, SEARCH_LIMIT);
        return ClanCapitalAttackerRes.create(clanTag, members.size());
    }

    public ClanCapitalUnderAttackerRes getClanCapitalUnderAttackers(String clanTag, int limit) {
        Clan clan = Clan.findByTag(clanTag);

        List<ClanCapitalRaidSeasonMember> members = getClanCapitalRaidSeasonsMembers(clan.getTag(), limit);
        List<ClanCapitalRaidSeasonMember> underAttackers = findUnderAttackers(members);

        return ClanCapitalUnderAttackerRes.create(clan.getName(), underAttackers.stream()
                                                                                .map(clanCapitalRaidSeasonMemberResConverter::convert)
                                                                                .collect(Collectors.toList()));
    }

    private List<ClanCapitalRaidSeasonMember> findUnderAttackers(List<ClanCapitalRaidSeasonMember> members) {
        if (ObjectUtils.isEmpty(members)) return Collections.emptyList();

        return members.stream()
                      .filter(ClanCapitalRaidSeasonMember::isUnderAttacks)
                      .collect(Collectors.toList());
    }

    public List<ClanCapitalAttackerRes> getCapitalAttackers() throws ExecutionException, InterruptedException {
        List<String> capitalClanTagList = Clan.getCapitalClanTagList();

        ForkJoinPool forkJoinPool = new ForkJoinPool(capitalClanTagList.size());
        return forkJoinPool.submit(() -> capitalClanTagList.stream()
                                                           .parallel()
                                                           .map(this::findClanCapitalRaidSeasons)
                                                           .collect(Collectors.toList()))
                           .get();
    }

    public List<ClanCapitalUnderAttackerRes> getCapitalMissingAttackers() throws ExecutionException, InterruptedException {
        List<String> capitalClanTagList = Clan.getCapitalClanTagList();

        ForkJoinPool forkJoinPool = new ForkJoinPool(capitalClanTagList.size());

        return forkJoinPool.submit(() -> capitalClanTagList.stream()
                                                           .parallel()
                                                           .map(clanTag -> getClanCapitalUnderAttackers(clanTag, 1))
                                                           .collect(Collectors.toList()))
                           .get();
    }

    public ClanCurrentWarRes getClanCurrentWar(String clanTag) {
        ClanWar clanCurrentWar = clanApiService.findClanCurrentWarByClanTag(clanTag)
                                               .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "현재 클랜 전쟁 조회 실패"));

        return clanCurrentWarResConverter.convert(clanCurrentWar);
    }

    public List<ClanRes> getClanList() {
        return Clan.getClanWarList()
                   .stream()
                   .map(ClanRes::create)
                   .collect(Collectors.toList());
    }

    public ClanMemberListRes findClanMembersByClanTag(String clanTag) {
        ClanMemberList clanMemberList = clanApiService.findClanMembersByClanTag(clanTag)
                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 사용자 조회 실패"));

        return clanMemberListResConverter.convert(clanMemberList);
    }
}
