package open.api.coc.clans.clean.infrastructure.clan.external.mapper;

import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.infrastructure.clan.external.dto.ClanResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanResponseMapper {

    @Mapping(target = "badgeUrl", source = "badgeUrls")
    @Mapping(target = "memberSize", source = "members")
    @Mapping(target = "members", source = "memberList")
    Clan toClan(ClanResponse clanResponse);

}
