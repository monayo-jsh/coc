package open.api.coc.clans.clean.domain.beginningMonthPlan.mapper;

import java.time.LocalDate;
import open.api.coc.clans.clean.domain.beginningMonthPlan.dto.BeginningMonthPlanCreateCommand;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonthPlan;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthPlanCreateRequest;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthPlanResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    config = MapStructConfig.class
)
public abstract class BeginningMonthPlanMapper {

    @Autowired
    private TimeUtils timeUtils;

    public abstract BeginningMonthPlan create(BeginningMonthPlanCreateCommand createCommand);

    @Mapping(target = "date", source = "date", qualifiedByName = "convertDateToLocalDate")
    @Mapping(target = "leagueWarDesc", source = "leagueWar")
    @Mapping(target = "clanWarDesc", source = "clanWar")
    @Mapping(target = "capitalDesc", source = "capital")
    public abstract BeginningMonthPlanCreateCommand toBeginningMonthPlanCreateCommand(BeginningMonthPlanCreateRequest request);

    @Mapping(target = "date", source = "date", qualifiedByName = "convertDateToTimestamp")
    @Mapping(target = "leagueWar", source = "leagueWarDesc")
    @Mapping(target = "clanWar", source = "clanWarDesc")
    @Mapping(target = "capital", source = "capitalDesc")
    public abstract BeginningMonthPlanResponse toBeginningMonthPlanResponse(BeginningMonthPlan plan);

    @Named(value = "convertDateToLocalDate")
    protected LocalDate map(Long date) {
        return timeUtils.toLocalDate(date);
    }

    @Named(value = "convertDateToTimestamp")
    protected Long map(LocalDate localDate) {
        return timeUtils.toEpochMilliSecond(localDate);
    }
}
