package open.api.coc.clans.clean.infrastructure.clan.persistence.mapper;

import open.api.coc.clans.clean.domain.common.model.IconUrl;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.database.entity.clan.ClanBadgeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanBadgeEntityMapper {


    @Mapping(target = "iconUrl.tiny", source = "tiny")
    @Mapping(target = "iconUrl.small", source = "small")
    @Mapping(target = "iconUrl.medium", source = "medium")
    @Mapping(target = "iconUrl.large", source = "large")
    ClanBadgeEntity toClanBadgeEntity(IconUrl badgeUrl);
}
