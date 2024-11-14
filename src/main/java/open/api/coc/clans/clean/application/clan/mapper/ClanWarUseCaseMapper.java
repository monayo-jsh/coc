package open.api.coc.clans.clean.application.clan.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    config = MapStructConfig.class
)
public abstract class ClanWarUseCaseMapper {

    @Autowired
    private TimeConverter timeConverter;

    public ClanWarQuery toClanWarQuery(Long startDate, Long endDate) {
        return ClanWarQuery.create(map(startDate), map(endDate));
    }

    @Mapping(target = "clanName", source = "clanNew.name")
    public abstract ClanWarResponse toClanWarResponse(ClanWarEntity clanWar);

    protected LocalDate map(Long time) {
        return timeConverter.toLocalDate(time);
    }

    protected long map(LocalDateTime time) {
        return timeConverter.toEpochMilliSecond(time);
    }
}
