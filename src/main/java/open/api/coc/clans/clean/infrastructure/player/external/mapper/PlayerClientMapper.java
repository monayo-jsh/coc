package open.api.coc.clans.clean.infrastructure.player.external.mapper;

import java.util.List;
import java.util.Map;
import open.api.coc.clans.clean.domain.player.config.PetConfig;
import open.api.coc.clans.clean.domain.player.config.SiegeMachineConfig;
import open.api.coc.clans.clean.domain.player.external.model.PlayerHeroEquipmentResponse;
import open.api.coc.clans.clean.domain.player.external.model.PlayerHeroResponse;
import open.api.coc.clans.clean.domain.player.external.model.PlayerResponse;
import open.api.coc.clans.clean.domain.player.external.model.PlayerTroopResponse;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.Player.PlayerBuilder;
import open.api.coc.clans.clean.domain.player.model.PlayerHero;
import open.api.coc.clans.clean.domain.player.model.PlayerHeroEquipment;
import open.api.coc.clans.clean.domain.player.model.PlayerPet;
import open.api.coc.clans.clean.domain.player.model.PlayerSiegeMachine;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerTroopsEntityMapper;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    config = MapStructConfig.class
)
public abstract class PlayerClientMapper {

    @Autowired
    private PlayerHeroResponseMapper heroResponseMapper;

    @Autowired
    private PlayerHeroEquipmentResponseMapper heroEquipmentResponseMapper;

    @Autowired
    private PlayerTroopsEntityMapper troopsEntityMapper;

    // from COC API
    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "clanTag", source = "clan.tag")
    @Mapping(target = "heroes", source = "heroes", qualifiedByName = "mapHeroes")
    @Mapping(target = "heroEquipments", source = "heroEquipment", qualifiedByName = "mapHeroEquipments")
    @Mapping(target = "pets", source = "troops", qualifiedByName = "mapPets")
    @Mapping(target = "siegeMachines", source = "troops", qualifiedByName = "mapSiegeMachines")
    public abstract Player toPlayer(PlayerResponse playerResponse);

    @AfterMapping
    protected void processAfterMapping(PlayerResponse playerResponse, @MappingTarget Player.PlayerBuilder builder) {
        if (builder == null) return;

        convertWearHeroEquipment(playerResponse, builder); // 영웅의 착용중인 장비 매핑

        builder.mappingPlayerTag(); // 플레이어 하위 항목 연관관계 매핑
    }

    private void convertWearHeroEquipment(PlayerResponse playerResponse, PlayerBuilder builder) {
        // 영웅에 착용중인 장비 데이터를 참조해서 영웅 장비 객체에 장비 착용 상태를 동기화한다.
        Map<String, PlayerHeroEquipment> heroEquipmentMap = builder.getHeroEquipmentMap();

        // 영웅의 착용중인 장비의 착용 플래그를 설정한다.
        for(PlayerHeroEquipmentResponse heroEquipment : playerResponse.getWearHeroEquipments()) {
            PlayerHeroEquipment targetHeroEquipment = heroEquipmentMap.get(heroEquipment.getName());
            targetHeroEquipment.wear();
        }

        // 영웅의 착용중인 장비 목록을 매핑한다.
        builder.mappingHeroWearEquipments();
    }

    @Named(value = "mapHeroes")
    protected List<PlayerHero> mapHeroes(List<PlayerHeroResponse> heroResponses) {
        return heroResponses.stream()
                            .filter(PlayerHeroResponse::isVillageHome)
                            .map(heroResponseMapper::toPlayerHero)
                            .toList();
    }

    @Named(value = "mapHeroEquipments")
    protected List<PlayerHeroEquipment> mapHeroEquipments(List<PlayerHeroEquipmentResponse> heroEquipmentResponses) {
        return heroEquipmentResponses.stream()
                                     .filter(PlayerHeroEquipmentResponse::isVillageHome)
                                     .map(heroEquipmentResponseMapper::toPlayerHeroEquipment)
                                     .toList();
    }

    @Named(value = "mapPets")
    protected List<PlayerPet> mapPets(List<PlayerTroopResponse> troopResponses) {
        return troopResponses.stream()
                             .filter(PlayerTroopResponse::isVillageHome)
                             .filter(this::isPet)
                             .map(troopsEntityMapper::toPlayerPet)
                             .toList();
    }

    private boolean isPet(PlayerTroopResponse troopResponse) {
        PetConfig config = PetConfig.findByName(troopResponse.getName());
        return config.isPet();
    }

    @Named(value = "mapSiegeMachines")
    protected List<PlayerSiegeMachine> mapSiegeMachines(List<PlayerTroopResponse> troopResponses) {
        return troopResponses.stream()
                             .filter(PlayerTroopResponse::isVillageHome)
                             .filter(this::isSiegeMachines)
                             .map(troopsEntityMapper::toPlayerSiegeMachine)
                             .toList();
    }

    private boolean isSiegeMachines(PlayerTroopResponse troopResponse) {
        SiegeMachineConfig config = SiegeMachineConfig.findByName(troopResponse.getName());
        return config.isSiegeMachine();
    }
}
