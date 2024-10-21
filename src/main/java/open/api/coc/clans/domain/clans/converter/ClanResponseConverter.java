package open.api.coc.clans.domain.clans.converter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.domain.clans.ClanCapitalResponse;
import open.api.coc.clans.domain.clans.ClanMemberResponse;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.external.coc.clan.domain.clan.Clan;
import open.api.coc.external.coc.clan.domain.clan.ClanMember;
import open.api.coc.external.coc.clan.domain.clan.ClanWarLeague;
import open.api.coc.external.coc.clan.domain.common.Label;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ClanResponseConverter implements Converter<Clan, ClanResponse> {

    private final IconUrlResponseConverter iconUrlResponseConverter;
    private final LabelResponseConverter labelResponseConverter;
    private final ClanCapitalResponseConverter clanCapitalResponseConverter;
    private final ClanMemberResponseConverter clanMemberResponseConverter;

    private final ClanContentResponseConverter clanContentResponseConverter;

    @Override
    public ClanResponse convert(Clan source) {
        return ClanResponse.builder()
                           .tag(source.getTag())
                           .name(source.getName())
                           .clanLevel(source.getClanLevel())
                           .clanPoints(source.getClanPoints())
                           .badgeUrls(iconUrlResponseConverter.convert(source.getBadgeUrls()))
                           .description(source.getDescription())
                           .type(source.getType())
                           .isFamilyFriendly(source.getIsFamilyFriendly())
                           .isWarLogPublic(source.getIsWarLogPublic())
                           .warFrequency(source.getWarFrequency())
                           .requiredTownhallLevel(source.getRequiredTownhallLevel())
                           .requiredTrophies(source.getRequiredTrophies())
                           .warWinStreak(source.getWarWinStreak())
                           .warWins(source.getWarWins())
                           .warLosses(source.getWarLosses())
                           .warTies(source.getWarTies())
                           .warLeague(labelResponseConverter.convert(source.getWarLeague()))
                           .labels(makeLabelResponse(source.getLabels()))
                           .capitalLeague(labelResponseConverter.convert(source.getCapitalLeague()))
                           .clanCapitalPoints(source.getClanCapitalPoints())
                           .clanCapital(clanCapitalResponseConverter.convert(source.getClanCapital()))
                           .members(source.getMembers())
                           .memberList(makeMemberList(source.getMemberList()))
                           .build();
    }

    public ClanResponse convert(ClanEntity clanEntity) {
        return ClanResponse.builder()
                           .tag(clanEntity.getTag())
                           .name(clanEntity.getName())
                           .warLeague(makeWarLeagueResponse(clanEntity.getWarLeague()))
                           .capitalLeague(makeClanCapitalLeagueResponse(clanEntity.getCapitalLeague()))
                           .clanCapitalPoints(clanEntity.getCapitalPoints())
                           .clanCapital(makeClanCapitalResponse(clanEntity.getCapitalHallLevel()))
                           .isMaxWar(clanEntity.getIsMaxWar())
                           .isOccupy(clanEntity.getIsOccupy())
                           .warDescription(clanEntity.getWarDescription())
                           .order(clanEntity.getOrder())
                           .clanContent(clanContentResponseConverter.convert(clanEntity.getClanContent()))
                           .build();
    }

    private LabelResponse makeWarLeagueResponse(String warLeague) {
        if (Objects.isNull(warLeague)) return null;
        return LabelResponse.builder()
                            .name(warLeague)
                            .build();
    }

    private LabelResponse makeClanCapitalLeagueResponse(String capitalLeague) {
        if (Objects.isNull(capitalLeague)) return null;
        return LabelResponse.builder()
                            .name(capitalLeague)
                            .build();
    }

    private ClanCapitalResponse makeClanCapitalResponse(Integer capitalHallLevel) {
        if (Objects.isNull(capitalHallLevel)) return null;
        return ClanCapitalResponse.builder()
                                  .capitalHallLevel(capitalHallLevel)
                                  .build();
    }

    private List<ClanMemberResponse> makeMemberList(List<ClanMember> clanMembers) {
        if (CollectionUtils.isEmpty(clanMembers)) return Collections.emptyList();

        return clanMembers.stream()
                          .map(clanMemberResponseConverter::convert)
                          .collect(Collectors.toList());
    }

    private List<LabelResponse> makeLabelResponse(List<Label> labels) {
        if (CollectionUtils.isEmpty(labels)) return Collections.emptyList();
        return labels.stream()
                     .map(labelResponseConverter::convert)
                     .collect(Collectors.toList());

    }

    public ClanResponse convert(ClanWarLeague clanWarLeague) {
        return ClanResponse.builder()
                           .tag(clanWarLeague.getTag())
                           .name(clanWarLeague.getName())
                           .clanLevel(clanWarLeague.getClanLevel())
                           .badgeUrls(iconUrlResponseConverter.convert(clanWarLeague.getBadgeUrls()))
                           .members(clanWarLeague.getMembers().size())
                           .memberList(makeMemberList(clanWarLeague.getMembers()))
                           .build();
    }
}