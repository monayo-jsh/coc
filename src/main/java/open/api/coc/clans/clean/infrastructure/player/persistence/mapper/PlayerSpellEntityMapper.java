package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerSpell;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerSpellEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlayerSpellEntityMapper {

    @Mapping(target = "name", source = "id.name")
    @Mapping(target = "level", source = "levelInfo.level")
    @Mapping(target = "maxLevel", source = "levelInfo.maxLevel")
    PlayerSpell toPlayerSpell(PlayerSpellEntity playerSpellEntity);

}
