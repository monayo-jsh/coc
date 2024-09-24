package open.api.coc.clans.clean.infrastructure.league.mapper;

import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.infrastructure.common.mapper.IconUrlEntityMapper;
import open.api.coc.clans.clean.infrastructure.league.entity.LeagueEntity;
import open.api.coc.external.coc.clan.domain.common.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = IconUrlEntityMapper.class
)
public interface LeagueEntityMapper {

    @Mapping(target = "iconUrl", source = "iconUrls")
    LeagueEntity toEntity(Label label);

    @Mapping(target = "iconUrl", source = "iconUrl")
    League toDomain(LeagueEntity entity);

}
