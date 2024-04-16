package open.api.coc.external.coc.clan.domain.clan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanMemberList {

    private List<ClanMember> items;

}
