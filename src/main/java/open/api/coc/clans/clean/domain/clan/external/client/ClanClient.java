package open.api.coc.clans.clean.domain.clan.external.client;

import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanMember;

public interface ClanClient {

    Clan findByTag(String clanTag);
    List<ClanMember> findMembersByTag(String clanTag);

}