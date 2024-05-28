package open.api.coc.clans.domain.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.database.entity.raid.RaiderEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class RaiderResponse {
    private String tag;
    private String name;
    private Integer capitalResourcesLooted;

    public static RaiderResponse toResponse(RaiderEntity raiderEntity) {
        return new RaiderResponse(raiderEntity.getTag(), raiderEntity.getName(), raiderEntity.getResourceLooted());
    }
}
