package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberPKEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClanWarMemberRepository extends JpaRepository<ClanWarMemberEntity, ClanWarMemberPKEntity> {

    @EntityGraph(attributePaths = "attacks")
    List<ClanWarMemberEntity> findByIdWarId(Long warId);

}
