package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalResponse {

    @Schema(description = "캐피탈 홀 레벨")
    private Integer capitalHallLevel;

}
