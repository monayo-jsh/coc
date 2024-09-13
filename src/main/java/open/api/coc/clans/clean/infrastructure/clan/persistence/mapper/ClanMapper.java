package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanMapper {

    @Mapping(target = "warLeague.name", source = "warLeague")
    Clan toDomain(ClanEntity clanEntity);

}
