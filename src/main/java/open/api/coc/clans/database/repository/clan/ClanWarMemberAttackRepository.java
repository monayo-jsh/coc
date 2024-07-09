package open.api.coc.clans.database.repository.clan;

import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanWarMemberAttackRepository extends JpaRepository<ClanWarMemberAttackEntity, ClanWarMemberAttackPKEntity> {

}
