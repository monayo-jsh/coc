package open.api.coc.clans.clean.infrastructure.common.external.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueResponse {

    private Integer id; // 고유키
    private String name; // 이름
    private IconUrlResponse iconUrl; // 아이콘 정보

}
