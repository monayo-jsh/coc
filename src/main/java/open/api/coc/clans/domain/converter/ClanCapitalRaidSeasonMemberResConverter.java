package open.api.coc.clans.domain.converter;

import open.api.coc.clans.domain.ClanCapitalRaidSeasonMemberRes;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasonMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClanCapitalRaidSeasonMemberResConverter implements Converter<ClanCapitalRaidSeasonMember, ClanCapitalRaidSeasonMemberRes> {

    @Override
    public ClanCapitalRaidSeasonMemberRes convert(ClanCapitalRaidSeasonMember source) {
        return ClanCapitalRaidSeasonMemberRes.builder()
                                             .tag(source.getTag())
                                             .name(source.getName())
                                             .attacks(source.getAttacks())
                                             .attackLimit(source.getAttackLimit())
                                             .bonusAttackLimit(source.getBonusAttackLimit())
                                             .build();
    }

}
