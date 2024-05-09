package open.api.coc.clans.service;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.AcademeClan;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.ClanContentEntity;
import open.api.coc.clans.database.entity.ClanEntity;
import open.api.coc.clans.database.repository.ClanContentRepository;
import open.api.coc.clans.database.repository.ClanRepository;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.ClanContent;
import open.api.coc.clans.domain.clans.ClanCurrentWarRes;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.LeagueClanRes;
import open.api.coc.clans.domain.clans.converter.ClanCapitalRaidSeasonResponseConverter;
import open.api.coc.clans.domain.clans.converter.ClanCurrentWarResConverter;
import open.api.coc.clans.domain.clans.converter.ClanMemberListResConverter;
import open.api.coc.clans.domain.clans.converter.ClanResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeason;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.Clan;
import open.api.coc.external.coc.clan.domain.clan.ClanMemberList;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClanApiService clanApiService;

    private final ClanRepository clanRepository;
    private final ClanContentRepository clanContentRepository;

    private final ClanResponseConverter clanResponseConverter;
    private final ClanCapitalRaidSeasonResponseConverter clanCapitalRaidSeasonResponseConverter;
    private final ClanCurrentWarResConverter clanCurrentWarResConverter;
    private final ClanMemberListResConverter clanMemberListResConverter;

    public ClanResponse findClanByClanTag(String clanTag) {
        Clan clan = clanApiService.findClanByClanTag(clanTag)
                                  .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 조회 실패"));

        return clanResponseConverter.convert(clan);
    }

    public ClanCurrentWarRes getClanCurrentWar(String clanTag) {
        ClanWar clanCurrentWar = clanApiService.findClanCurrentWarByClanTag(clanTag)
                                               .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "현재 클랜 전쟁 조회 실패"));

        return clanCurrentWarResConverter.convert(clanCurrentWar);
    }


    public ClanCurrentWarRes getLeagueWar(String clanTag, String roundTag) {
        ClanWar leagueWar = clanApiService.findLeagueWarByRoundTag(roundTag)
                .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "리그전 조회 실패"));
        leagueWar.swapWarClan(clanTag);
        return clanCurrentWarResConverter.convert(leagueWar);
    }

    public List<ClanResponse> getClanList() {
        List<ClanEntity> clans = clanRepository.findAll();

        return clans.stream()
                    .map(clanResponseConverter::convert)
                    .sorted(Comparator.comparing(ClanResponse::getOrder))
                    .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanWarResList() {
        //@TODO 추 후 동적으로 관리하도록 수정하기
        return AcademeClan.getClanWarList()
                          .stream()
                          .map(ClanResponse::create)
                          .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanWarParallelResList() {
        // 병행클전 대상 클랜 목록
        //@TODO 추 후 동적으로 관리하도록 수정하기
        return AcademeClan.getClanWarParallelList()
                          .stream()
                          .map(ClanResponse::create)
                          .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanCaptialList() {
        //@TODO 추 후 동적으로 관리하도록 수정하기
        return AcademeClan.getClanCapitalList()
                          .stream()
                          .map(ClanResponse::create)
                          .collect(Collectors.toList());
    }

    public LeagueClanRes getLeagueClan(String clanTag) throws IOException {
        AcademeClan clan = AcademeClan.findByTag(clanTag);
        return LeagueClanRes.create(clan);
    }


    public ClanMemberListRes findClanMembersByClanTag(String clanTag) {
        ClanMemberList clanMemberList = clanApiService.findClanMembersByClanTag(clanTag)
                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 사용자 조회 실패"));

        ClanMemberListRes clanMemberListRes = clanMemberListResConverter.convert(clanMemberList);
        clanMemberListRes.setClanTag(clanTag);
        return clanMemberListRes;
    }

    public ClanCapitalRaidSeasonResponse getClanCapitalRaidSeason(String clanTag) {
        final int SEARCH_LIMIT = 1;
        ClanCapitalRaidSeasons clanCapitalRaidSeasons = clanApiService.findClanCapitalRaidSeasonsByClanTagAndLimit(clanTag, SEARCH_LIMIT)
                                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜캐피탈 조회 실패"));

        if (clanCapitalRaidSeasons.isEmpty()) {
            throw CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜캐피탈 조회 실패");
        }

        ClanCapitalRaidSeason clanCapitalRaidSeason = clanCapitalRaidSeasons.getItemWithMembers();

        return clanCapitalRaidSeasonResponseConverter.convert(clanCapitalRaidSeason);
    }

    public List<ClanMemberListRes> findClanMembersByClanTags(List<String> clanTags) {
        return clanTags.stream()
                       .parallel()
                       .map(this::findClanMembersByClanTag)
                       .collect(Collectors.toList());
    }

    public List<ClanResponse> findClanByClanTags(List<String> clanTags) {
        return clanTags.stream()
                       .parallel()
                       .map(clanApiService::findClanByClanTag)
                       .filter(Optional::isPresent)
                       .map(clan -> clanResponseConverter.convert(clan.get()))
                       .collect(Collectors.toList());
    }

    @Transactional
    public void updateClanContentStatus(ClanContent request) {

        ClanContentEntity clanContent = clanContentRepository.findById(request.getTag())
                                                             .orElseThrow(() -> createNotFoundException("클랜[%s] 컨텐츠 정보".formatted(request.getTag())));

        if (StringUtils.hasText(request.getClanWarYn())) {
            clanContent.setClanWarYn(request.getClanWarYn());
        }

        if (StringUtils.hasText(request.getClanWarLeagueYn())) {
            clanContent.setWarLeagueYn(request.getClanWarLeagueYn());
        }

        if (StringUtils.hasText(request.getClanCapitalYn())) {
            clanContent.setClanCapitalYn(request.getClanCapitalYn());
        }

        if (StringUtils.hasText(request.getClanWarParallelYn())) {
            clanContent.setClanWarParallelYn(request.getClanWarParallelYn());
        }

        clanContentRepository.save(clanContent);
    }

    @Transactional
    public void deleteClan(String clanTag) {
        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElse(null);

        if (ObjectUtils.isEmpty(clan)) {
            // 클랜 없는 경우 성공
            return;
        }

        clanRepository.deleteById(clanTag);
        clanContentRepository.deleteById(clanTag);
    }
}
