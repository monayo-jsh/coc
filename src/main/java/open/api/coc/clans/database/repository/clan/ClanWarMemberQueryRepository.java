package open.api.coc.clans.database.repository.clan;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;

public interface ClanWarMemberQueryRepository {

    List<ClanWarMemberEntity> findAllByWarId(Long warId);

}
