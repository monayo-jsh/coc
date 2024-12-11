package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.ClanGame;
import open.api.coc.clans.clean.domain.clan.model.ClanGameMeta;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanGameEntity;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanGameMetaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanGameEntityMapper {

    ClanGameMeta toClanGameMeta(ClanGameMetaEntity clanGameMetaEntity);

    ClanGame toClanGame(ClanGameEntity clanGameEntity);

    ClanGameEntity toClanGameEntity(ClanGame clanGame);


}
