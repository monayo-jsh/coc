package open.api.coc.clans.clean.infrastructure.common.persistence.mapper;

import open.api.coc.clans.clean.infrastructure.common.persistence.entity.IconUrlEntity;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IconUrlEntityMapper {

    IconUrlEntity toEntity(IconUrl iconUrl);

}
