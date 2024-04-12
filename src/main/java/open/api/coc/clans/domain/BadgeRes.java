package open.api.coc.clans.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BadgeRes {

    private String small;
    private String medium;
    private String large;

}
