package open.api.coc.clans.clean.domain.league.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.common.model.IconUrl;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class League {

    private Integer id;
    private String name;
    private IconUrl iconUrl;

}
