package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class League {

    private Integer id;
    private String name;
    private IconUrl iconUrls;

}
