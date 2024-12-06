package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        ClanContentEntityMapper.class,
        ClanBadgeEntityMapper.class
    }
)
public interface ClanEntityMapper {

    @Mapping(target = "warLeague.name", source = "warLeague")
    @Mapping(target = "capitalLeague.name", source = "capitalLeague")
    @Mapping(target = "clanCapital.capitalHallLevel", source = "capitalHallLevel")
    @Mapping(target = "clanCapital.tier", source = "capitalTier")
    @Mapping(target = "clanCapitalPoints", source = "capitalPoints")
    @Mapping(target = "badgeUrl", source = "badgeUrl.iconUrl")
    @Mapping(target = "clanContent.clanWarLeagueYn", source = "clanContent.warLeagueYn")
    Clan toClan(ClanEntity clanEntity);

    @Mapping(target = "warLeague", source = "warLeague.name")
    @Mapping(target = "capitalLeague", source = "capitalLeague.name")
    @Mapping(target = "capitalHallLevel", source = "clanCapital.capitalHallLevel")
    @Mapping(target = "capitalTier", source = "clanCapital.tier")
    @Mapping(target = "capitalPoints", source = "clanCapitalPoints")
    @Mapping(target = "clanContent", ignore = true)
    @Mapping(target = "badgeUrl", ignore = true)
    ClanEntity toClanEntity(Clan clan);

}
