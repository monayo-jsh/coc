package open.api.coc.clans.clean.domain.player.model.dto;

import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RankingHeroEquipmentDTO(

    // 영웅 이름
    String heroName,

    // 착용 장비 목록
    List<String> equipmentNames,

    // 영웅별 착용 장비 세트 개수
    Long count,

    // 영웅별 장비 세트 전체 개수
    Long totalCount

) {

    public static RankingHeroEquipmentDTO create(Object[] result) {
        String heroName = (String) result[0];
        List<String> equipmentNames = Arrays.asList((String[]) result[1]);
        Long count = (Long) result[2];
        Long totalCount = (Long) result[3];
        return RankingHeroEquipmentDTO.builder()
                                      .heroName(heroName)
                                      .equipmentNames(equipmentNames)
                                      .count(count)
                                      .totalCount(totalCount)
                                      .build();
    }

}
