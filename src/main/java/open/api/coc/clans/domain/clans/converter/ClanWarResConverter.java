package open.api.coc.clans.domain.clans.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanWarMemberRes;
import open.api.coc.clans.domain.clans.ClanWarRes;
import open.api.coc.external.coc.clan.domain.clan.ClanWarMember;
import open.api.coc.external.coc.clan.domain.clan.WarClan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ClanWarResConverter implements Converter<WarClan, ClanWarRes> {

    private final IconUrlResConverter iconUrlResConverter;
    private final ClanWarMemberResConverter clanWarMemberResConverter;

    @Override
    public ClanWarRes convert(WarClan source) {
        return ClanWarRes.builder()
                         .name(source.getName())
                         .tag(source.getTag())
                         .badge(iconUrlResConverter.convert(source.getBadgeUrls()))
                         .stars(source.getStars())
                         .attacks(source.getAttacks())
                         .destructionPercentage(source.getDestructionPercentage())
                         .members(makeMembers(source.getMembers()))
                         .build();
    }

    private List<ClanWarMemberRes> makeMembers(List<ClanWarMember> members) {
        if (CollectionUtils.isEmpty(members)) return Collections.emptyList();
        return members.stream()
                      .map(clanWarMemberResConverter::convert)
                      .collect(Collectors.toList());
    }

}