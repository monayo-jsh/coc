package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlayerRecordRepository extends JpaRepository<PlayerRecordEntity, PlayerRecordPK> {

    @Query(
        value = "select new open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO(pr.id.tag, pr.order, p.name, p.expLevel, p.trophies) "
            + "from PlayerRecordEntity pr "
            + "join PlayerEntity p on p.playerTag = pr.id.tag "
            + "where p.name like concat(:name, '%') "
            + "or p.nickname like concat(:name, '%') "
            + "order by pr.id.tag ")
    List<PlayerLegendRecordTargetDTO> findAllByNameOrNickname(String name);

}
