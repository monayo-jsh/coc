package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerHeroEquipment;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEquipmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlayerHeroEquipmentEntityMapper {

    @Mapping(target = "id.playerTag", source = "playerTag")
    @Mapping(target = "id.name", source = "name")
    @Mapping(target = "levelInfo.level", source = "level")
    @Mapping(target = "levelInfo.maxLevel", source = "maxLevel")
    @Mapping(target = "targetHeroName", source = "config.hero.name")
    @Mapping(target = "player.playerTag", source = "playerTag")
    PlayerHeroEquipmentEntity toPlayerHeroEquipmentEntity(PlayerHeroEquipment playerHeroEquipment);

    @Mapping(target = "playerTag", source = "id.playerTag")
    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerHeroEquipment toPlayerHeroEquipment(PlayerHeroEquipmentEntity playerHeroEquipmentEntity);

}
