package open.api.coc.clans.clean.infrastructure.clan.external.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClanMemberListResponse {

    @Builder.Default
    private List<ClanMemberResponse> items = new ArrayList<>();

}
