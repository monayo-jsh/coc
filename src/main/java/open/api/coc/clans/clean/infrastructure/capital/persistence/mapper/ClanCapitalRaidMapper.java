package open.api.coc.clans.clean.infrastructure.capital.persistence.mapper;

import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = ClanCapitalRaidMemberMapper.class
)
public interface ClanCapitalRaidMapper {

    @Mapping(target = "raiders", ignore = true)
    RaidEntity toRaidEntity(ClanCapitalRaid clanCapitalRaid);

    @Mapping(target = "raiders", source = "members")
    RaidEntity toRaidEntityWithRaiderEntity(ClanCapitalRaid clanCapitalRaid);

    @Mapping(target = "members", ignore = true)
    ClanCapitalRaid toClanCapitalRaid(RaidEntity entity);

    @Mapping(target = "members", source = "raiders")
    ClanCapitalRaid toClanCapitalRaidWithMembers(RaidEntity entity);

}
