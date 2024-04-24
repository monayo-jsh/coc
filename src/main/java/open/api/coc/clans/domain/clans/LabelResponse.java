package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LabelResponse {

    private Integer id;
    private String name;
    private IconUrlResponse iconUrls;

}
