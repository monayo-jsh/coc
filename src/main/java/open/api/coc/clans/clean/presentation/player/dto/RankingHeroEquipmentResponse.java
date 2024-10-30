package open.api.coc.clans.clean.presentation.player.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;

@Getter
@Builder
@AllArgsConstructor
public class RankingHeroEquipmentResponse {

    private String heroName; // 영웅 이름
    private List<HeroEquipmentResponse> heroEquipments; // 착용 장비 목록
    private Long count; // 착용 장비 세트 개수
    private Long totalCount; // 장비 세트 전체 개수

}
