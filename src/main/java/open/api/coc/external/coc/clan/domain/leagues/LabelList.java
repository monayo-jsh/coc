package open.api.coc.external.coc.clan.domain.leagues;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.common.Label;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelList {

    private List<Label> items;

}
