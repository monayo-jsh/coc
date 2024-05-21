package open.api.coc.clans.database.entity.raid.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonMemberResponse;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeason;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasonMember;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RaidEntityConverter {
    private final TimeConverter timeConverter;
    private final RaiderEntityConverter raiderEntityConverter;

    public RaidEntity convert(String clanTag, ClanCapitalRaidSeasonResponse source) {
        return RaidEntity.builder()
                .clanTag(clanTag)
                .startDate(timeConverter.toLocalDate(source.getStartTime()))
                .endDate(timeConverter.toLocalDate(source.getEndTime()))
                .radierEntityList(makeMembers(source.getMembers())).build();
    }

    private List<RaiderEntity> makeMembers(List<ClanCapitalRaidSeasonMemberResponse> members) {
        if (CollectionUtils.isEmpty(members)) return Collections.emptyList();

        return members.stream()
                .map(raiderEntityConverter::convert)
                .collect(Collectors.toList());
    }
}
