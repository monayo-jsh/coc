package open.api.coc.clans.domain.raid.conveter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.raid.ClanCapitalRaidSeasonMemberResponse;
import open.api.coc.clans.domain.raid.ClanCapitalRaidSeasonResponse;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeason;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasonMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ClanCapitalRaidSeasonResponseConverter implements Converter<ClanCapitalRaidSeason, ClanCapitalRaidSeasonResponse> {

    private final ClanCapitalRaidSeasonMemberResponseConverter clanCapitalRaidSeasonMemberResponseConverter;
    private final TimeConverter timeConverter;

    @Override
    public @NonNull ClanCapitalRaidSeasonResponse convert(ClanCapitalRaidSeason source) {
        return ClanCapitalRaidSeasonResponse.builder()
                                            .state(source.getState())
                                            .startTime(timeConverter.toEpochMilliSecond(source.getStartTime()))
                                            .endTime(timeConverter.toEpochMilliSecond(source.getEndTime()))
                                            .members(makeMembers(source.getMembers()))
                                            .build();
    }

    private List<ClanCapitalRaidSeasonMemberResponse> makeMembers(List<ClanCapitalRaidSeasonMember> members) {
        if (CollectionUtils.isEmpty(members)) return Collections.emptyList();

        return members.stream()
                      .map(clanCapitalRaidSeasonMemberResponseConverter::convert)
                      .collect(Collectors.toList());
    }

}
