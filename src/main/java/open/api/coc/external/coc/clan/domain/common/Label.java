package open.api.coc.external.coc.clan.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Label {

    private Integer id;
    private String name;
    private IconUrl iconUrls;

}
