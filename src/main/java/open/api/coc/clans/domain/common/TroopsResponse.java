package open.api.coc.clans.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TroopsResponse {

    private String name;
    private String level;
    private String maxLevel;
    private String village;

}
