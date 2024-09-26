package open.api.coc.clans.clean.domain.capital.service.strategy;

import java.util.List;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalMemberService;

public class RaiderScorePlayerTagQueryStrategy implements RaiderScoreQueryStrategy {

    @Override
    public List<ClanCapitalRaidMember> execute(ClanCapitalMemberService clanCapitalMemberService, String playerTag, Integer limit) {
        return clanCapitalMemberService.findByTag(playerTag, limit);
    }

}
