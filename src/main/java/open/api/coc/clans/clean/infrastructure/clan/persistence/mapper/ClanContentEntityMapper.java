package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.clan.model.ClanContent;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanContentEntityMapper {

    @Mapping(target = "warLeagueYn", source = "clanWarLeagueYn")
    ClanContentEntity toClanContentEntity(ClanContent clan);

}
