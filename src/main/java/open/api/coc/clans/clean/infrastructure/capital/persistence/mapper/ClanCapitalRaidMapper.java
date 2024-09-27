package open.api.coc.clans.clean.infrastructure.capital.persistence.mapper;

import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

    // 매핑 후 양방향 관계 설정
    @AfterMapping
    default void establishBidirectionalRelationship(@MappingTarget RaidEntity raidEntity) {
        if (raidEntity.getRaiders() != null) {
            raidEntity.getRaiders().forEach(raider -> raider.changeRaid(raidEntity));
        }
    }
}
