package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerPet;
import open.api.coc.clans.clean.domain.player.model.PlayerSiegeMachine;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlayerTroopsEntityMapper {

    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerPet toPlayerPet(PlayerTroopsEntity playerTroopsEntity);

    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerSiegeMachine toPlayerSiegeMachine(PlayerTroopsEntity playerTroopsEntity);
}
