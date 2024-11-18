package open.api.coc.clans.clean.application.clan.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import org.mapstruct.Mapper;
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

    public abstract ClanWarResponse toClanWarResponse(ClanWarDTO clanWar);

    public abstract ClanWarDetailResponse toClanWarDetailResponse(ClanWarDTO clanWar);

    protected LocalDate map(Long time) {
        return timeConverter.toLocalDate(time);
    }

    protected long map(LocalDateTime time) {
        return timeConverter.toEpochMilliSecond(time);
    }

}
