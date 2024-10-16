package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import java.util.List;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.PlayerPet;
import open.api.coc.clans.clean.domain.player.model.PlayerSiegeMachine;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        PlayerHeroEntityMapper.class,
        PlayerSpellEntityMapper.class,
        PlayerHeroEquipmentEntityMapper.class
    }
)
public abstract class PlayerEntityMapper {

    @Autowired
    private PlayerTroopsEntityMapper playerTroopsEntityMapper;

    @Mapping(target = "tag", source = "playerTag")
    @Mapping(target = "clanTag", source = "clan.tag")
    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "pets", source = "troops", qualifiedByName = "mapPets")
    @Mapping(target = "siegeMachines", source = "troops", qualifiedByName = "mapSiegeMachines")
    public abstract Player toPlayer(PlayerEntity playerEntity);

    @AfterMapping
    protected void fillHeroWearEquipments(PlayerEntity playerEntity, @MappingTarget Player.PlayerBuilder playerBuilder) {
        if (playerBuilder == null) return;
        playerBuilder.mappingHeroWearEquipments();
    }

    @Named(value = "mapPets")
    protected List<PlayerPet> mapPets(List<PlayerTroopsEntity> troopsEntities) {
        return troopsEntities.stream()
                             .filter(PlayerTroopsEntity::isPet)
                             .map(playerTroopsEntityMapper::toPlayerPet)
                             .toList();
    }

    @Named(value = "mapSiegeMachines")
    protected List<PlayerSiegeMachine> mapSiegeMachines(List<PlayerTroopsEntity> troopsEntities) {
        return troopsEntities.stream()
                             .filter(PlayerTroopsEntity::isSiegeMachine)
                             .map(playerTroopsEntityMapper::toPlayerSiegeMachine)
                             .toList();
    }

}
