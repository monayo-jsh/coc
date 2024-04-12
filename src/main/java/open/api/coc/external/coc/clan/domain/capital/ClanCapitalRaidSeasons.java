package open.api.coc.external.coc.clan.domain.capital;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalRaidSeasons {

    private List<ClanCapitalRaidSeason> items;

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(items);
    }

    public ClanCapitalRaidSeason getItemWithMembers() {
        return items.stream()
                    .filter(item -> !item.getMembers().isEmpty())
                    .findFirst()
                    .orElseGet(ClanCapitalRaidSeason::new);
    }
}
