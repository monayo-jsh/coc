package open.api.coc.clans.clean.application.raid.query.strategy;

import java.util.List;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalMemberService;

public interface RaiderScoreQueryStrategy {

    List<ClanCapitalRaidMember> execute(ClanCapitalMemberService clanCapitalMemberService, String criteria, Integer limit);

}