package open.api.coc.clans.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HeroEquipmentResponse {

    private Integer code;

    private String name;
    private String village;

    private Integer level;
    private Integer maxLevel;

}
