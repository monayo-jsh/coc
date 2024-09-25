package open.api.coc.clans.clean.infrastructure.capital.persistence.mapper;

import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanCapitalRaidMemberMapper {

    @Mapping(target = "resourceLooted", source = "capitalResourcesLooted")
    @Mapping(target = "raid.id", source = "raidId")
    RaiderEntity toEntity(ClanCapitalRaidMember clanCapitalRaidMember);

    @Mapping(target = "capitalResourcesLooted", source = "resourceLooted")
    @Mapping(target = "raidId", source = "raid.id")
    ClanCapitalRaidMember toDomain(RaiderEntity entity);

}
