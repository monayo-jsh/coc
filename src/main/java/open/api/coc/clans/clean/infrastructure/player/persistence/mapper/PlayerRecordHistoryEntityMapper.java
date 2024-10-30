package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordHistoryEntity;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface PlayerRecordHistoryEntityMapper {


    PlayerRecordHistoryEntity toPlayerRecordHistoryEntity(PlayerRecordHistory recordHistory);

    @Mapping(target = "tag", source = "player.playerTag")
    @Mapping(target = "name", source = "player.name")
    PlayerRecordHistory toPlayerRecordHistory(PlayerRecordHistoryEntity playerRecordHistoryEntity);
}
