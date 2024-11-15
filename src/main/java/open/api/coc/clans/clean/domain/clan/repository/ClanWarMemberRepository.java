package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;

public interface ClanWarMemberRepository {

    List<ClanWarMemberEntity> findAllById(Long warId);

}
