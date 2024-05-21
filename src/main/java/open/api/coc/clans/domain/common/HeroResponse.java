package open.api.coc.clans.domain.common;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HeroResponse {

    private Integer code;

    private String name;
    private String koreanName;
    private String village;
    private Integer level;
    private Integer maxLevel;

    private List<HeroEquipmentResponse> equipments;

    public boolean isHomeHero() {
        return Objects.equals("home", village);
    }
}