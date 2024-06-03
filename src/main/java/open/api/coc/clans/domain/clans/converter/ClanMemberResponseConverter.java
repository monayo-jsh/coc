package open.api.coc.clans.domain.clans.converter;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanMemberResponse;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.external.coc.clan.domain.clan.ClanMember;
import open.api.coc.external.coc.clan.domain.common.Label;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanMemberResponseConverter implements Converter<ClanMember, ClanMemberResponse> {

    private final LabelResponseConverter labelResponseConverter;

    @Override
    public ClanMemberResponse convert(ClanMember source) {
        return ClanMemberResponse.builder()
                                 .name(source.getName())
                                 .tag(source.getTag())
                                 .role(source.getRole())
                                 .townHallLevel(source.getTownHallLevel())
                                 .expLevel(source.getExpLevel())
                                 .trophies(source.getTrophies())
                                 .clanRank(source.getClanRank())
                                 .previousClanRank(source.getPreviousClanRank())
                                 .donations(source.getDonations())
                                 .donationsReceived(source.getDonationsReceived())
                                 .league(makeLeague(source.getLeague()))
                                 .build();
    }

    private LabelResponse makeLeague(Label league) {
        if (Objects.isNull(league)) return null;
        return labelResponseConverter.convert(league);
    }

}