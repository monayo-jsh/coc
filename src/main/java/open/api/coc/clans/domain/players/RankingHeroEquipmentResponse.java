package open.api.coc.clans.domain.players;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;

@Getter
@Builder
@AllArgsConstructor
public class RankingHeroEquipmentResponse {

    private String heroName;
    private List<HeroEquipmentResponse> heroEquipments;
    private Integer count;
    private Integer totalCount;

}
