package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.ClanGame;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanGameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanGameEntityMapper {

    ClanGame toClanGame(ClanGameEntity clanGameEntity);

    ClanGameEntity toClanGameEntity(ClanGame clanGame);

}
