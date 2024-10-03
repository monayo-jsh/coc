package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.external.model.PlayerTroopResponse;
import open.api.coc.clans.clean.domain.player.model.PlayerPet;
import open.api.coc.clans.clean.domain.player.model.PlayerSiegeMachine;
import open.api.coc.clans.clean.domain.player.model.PlayerSpell;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlayerTroopsEntityMapper {

    @Mapping(target = "id.playerTag", source = "playerTag")
    @Mapping(target = "id.name", source = "name")
    @Mapping(target = "levelInfo.level", source = "level")
    @Mapping(target = "levelInfo.maxLevel", source = "maxLevel")
    PlayerTroopsEntity toPlayerPetEntity(PlayerPet playerPet);

    @Mapping(target = "id.playerTag", source = "playerTag")
    @Mapping(target = "id.name", source = "name")
    @Mapping(target = "levelInfo.level", source = "level")
    @Mapping(target = "levelInfo.maxLevel", source = "maxLevel")
    PlayerTroopsEntity toPlayerSiegeMachineEntity(PlayerSiegeMachine playerSiegeMachine);

    // from Entity
    @Mapping(target = "playerTag", source = "id.playerTag")
    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerPet toPlayerPet(PlayerTroopsEntity playerTroopsEntity);

    // from Entity
    @Mapping(target = "playerTag", source = "id.playerTag")
    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerSiegeMachine toPlayerSiegeMachine(PlayerTroopsEntity playerTroopsEntity);


    // from COC API
    PlayerPet toPlayerPet(PlayerTroopResponse playerTroopResponse);

    // from COC API
    PlayerSiegeMachine toPlayerSiegeMachine(PlayerTroopResponse playerTroopResponse);

}
