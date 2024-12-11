package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaPlayerHeroEquipmentRepository extends JpaRepository<PlayerHeroEquipmentEntity, String> {

    @Query(
        nativeQuery = true,
        value =
            " select "
                + " m.heroName,"
                + " m.equipmentNames, "
                + " m.count, "
                + " sum(m.count) over(partition by m.heroName) as totalCount"
                + " from ("
                + "  select target_hero_name as heroName, equipments as equipmentNames, count(equipments) as count"
                + "  from ("
                + "   select player_tag, target_hero_name, array_agg(name) as equipments"
                + "   from ("
                + "    select player_tag, target_hero_name, name"
                + "    from tb_player_hero_equipment"
                + "    where player_tag in (:playerTags)"
                + "    and wear_yn = 'Y'"
                + "    order by player_tag, target_hero_name, name"
                + "   )"
                + "   group by player_tag, target_hero_name) x"
                + "   group by x.target_hero_name, x.equipments"
                + "   order by x.target_hero_name, count desc"
                + " ) m"
    )
    List<RankingHeroEquipmentDTO> findRankingHeroEquipmentByTags(List<String> playerTags);

}
