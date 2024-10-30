package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaPlayerHeroEquipmentCustomRepository {

    final private EntityManager entityManager;

    public List<RankingHeroEquipmentDTO> findRankingHeroEquipmentByTags(List<String> playerTags) {

        String query = " select "
                           + "  m.heroName,"
                           + "  m.equipmentNames, "
                           + "  m.count, "
                           + "  cast(sum(m.count) over(partition by m.heroName) as long) as totalCount"
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
                           + " ) m";

        List<Object[]> results = entityManager.createNativeQuery(query)
                                              .setParameter("playerTags", playerTags)
                                              .getResultList();

        return results.stream()
                      .map(RankingHeroEquipmentDTO::create)
                      .toList();
    }

}
