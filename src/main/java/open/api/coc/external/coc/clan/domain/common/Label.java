package open.api.coc.external.coc.clan.domain.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Label {

    private Integer id;
    private String name;
    private IconUrl iconUrls;

}
