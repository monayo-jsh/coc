package open.api.coc.clans.clean.infrastructure.capital.external.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalRaidSeasonsResponse {

    private List<ClanCapitalRaidSeasonResponse> items;

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(items);
    }

    public ClanCapitalRaidSeasonResponse getItemWithMembers() {
        return items.stream()
                    .filter(item -> !item.getMembers().isEmpty())
                    .findFirst()
                    .orElse(null);
    }
}
