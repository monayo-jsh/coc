package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordHistoryEntity;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(
    config = MapStructConfig.class
)
public interface PlayerRecordHistoryEntityMapper {


    PlayerRecordHistoryEntity toPlayerRecordHistoryEntity(PlayerRecordHistory recordHistory);

}
