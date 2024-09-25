package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.infrastructure.common.persistence.mapper.IconUrlEntityMapper;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = IconUrlEntityMapper.class
)
public interface ClanMapper {

    @Mapping(target = "warLeague.name", source = "warLeague")
    @Mapping(target = "badgeUrls", source = "badgeUrl.iconUrl")
    Clan toDomain(ClanEntity clanEntity);

}
