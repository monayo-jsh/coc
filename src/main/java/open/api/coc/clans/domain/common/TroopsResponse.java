package open.api.coc.clans.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class TroopsResponse {

    private String name;

    @Setter
    private String koreanName;

    private Integer level;
    private Integer maxLevel;
    private String village;

    private String type;
    private Integer order;

}
