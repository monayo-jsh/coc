package open.api.coc.clans.database.repository.clan;

import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanWarMemberRepository extends JpaRepository<ClanWarMemberEntity, ClanWarMemberPKEntity> {

}
