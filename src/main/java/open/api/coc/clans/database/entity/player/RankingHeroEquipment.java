package open.api.coc.clans.database.entity.player;

import java.util.List;

public interface RankingHeroEquipment {

    String getHeroName();
    List<String> getEquipments();
    Integer getCount();
    Integer getTotalCount();
}
