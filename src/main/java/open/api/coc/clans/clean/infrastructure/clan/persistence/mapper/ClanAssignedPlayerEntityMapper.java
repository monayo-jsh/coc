package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.ClanAssignedPlayer;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanAssignedPlayerEntityMapper {

    @Mapping(target = "assignedDate", source = "id.seasonDate")
    @Mapping(target = "playerTag", source = "id.playerTag")
    @Mapping(target = "clanTag", source = "clan.tag")
    ClanAssignedPlayer toClanAssignedPlayer(ClanAssignedPlayerEntity clanAssignedPlayerEntity);

}
