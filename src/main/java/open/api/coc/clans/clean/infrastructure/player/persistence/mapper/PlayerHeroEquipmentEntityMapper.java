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

    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerHeroEquipment toPlayerHeroEquipment(PlayerHeroEquipmentEntity playerHeroEquipmentEntity);

}
