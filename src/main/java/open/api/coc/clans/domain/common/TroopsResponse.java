package open.api.coc.clans.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TroopsResponse {

    private String name;
    private Integer level;
    private Integer maxLevel;
    private String village;

}
