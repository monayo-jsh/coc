package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;
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
        PlayerHeroEquipmentEntityMapper.class,
        PlayerSpellEntityMapper.class
    }
)
public abstract class PlayerEntityMapper {

    @Autowired
    private PlayerTroopsEntityMapper playerTroopsEntityMapper;

    @Mapping(target = "playerTag", source = "tag")
    // 연관관계 매핑은 리포지토리 구현체에서 처리
    @Mapping(target = "clan", ignore = true)
    @Mapping(target = "league", ignore = true)
    @Mapping(target = "heroes", ignore = true)
    @Mapping(target = "heroEquipments", ignore = true)
    @Mapping(target = "troops", ignore = true)
    @Mapping(target = "spells", ignore = true)
    public abstract PlayerEntity toEntity(Player newPlayer);

    @Mapping(target = "tag", source = "playerTag")
    @Mapping(target = "clanTag", source = "clan.tag")
    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "heroes", ignore = true)
    @Mapping(target = "heroEquipments", ignore = true)
    @Mapping(target = "spells", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "siegeMachines", ignore = true)
    public abstract Player toPlayerOnly(PlayerEntity playerEntity);

    @Mapping(target = "tag", source = "playerTag")
    @Mapping(target = "clanTag", source = "clan.tag")
    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "pets", source = "troops", qualifiedByName = "mapPets")
    @Mapping(target = "siegeMachines", source = "troops", qualifiedByName = "mapSiegeMachines")
    public abstract Player toPlayer(PlayerEntity playerEntity);

    @Mapping(target = "tag", source = "playerTag")
    @Mapping(target = "clanTag", source = "clan.tag")
    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "spells", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "siegeMachines", ignore = true)
    public abstract Player toSummarizedPlayer(PlayerEntity playerEntity);

    @AfterMapping
    protected void afterMappingToPlayer(PlayerEntity playerEntity, @MappingTarget Player.PlayerBuilder playerBuilder) {
        if (playerBuilder == null) return;
        playerBuilder.mappingHeroWearEquipments();
    }

    @Named(value = "mapPets")
    protected List<PlayerPet> mapPets(List<PlayerTroopsEntity> troopsEntities) {
        return troopsEntities.stream()
                             .filter(PlayerTroopsEntity::isPet)
                             .map(playerTroopsEntityMapper::toPlayerPet)
                             .collect(Collectors.toList());
    }

    @Named(value = "mapSiegeMachines")
    protected List<PlayerSiegeMachine> mapSiegeMachines(List<PlayerTroopsEntity> troopsEntities) {
        return troopsEntities.stream()
                             .filter(PlayerTroopsEntity::isSiegeMachine)
                             .map(playerTroopsEntityMapper::toPlayerSiegeMachine)
                             .collect(Collectors.toList());
    }

}
