package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class IconUrlResponse {

    private String tiny;
    private String small;
    private String medium;
    private String large;

}
