package open.api.coc.clans.clean.domain.beginningMonthPlan.mapper;

import java.time.LocalDate;
import open.api.coc.clans.clean.domain.beginningMonthPlan.dto.BeginningMonthPlanCreateCommand;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonthPlan;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthPlanCreateRequest;
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

    @Mapping(target = "date", source = "date", qualifiedByName = "convertDate")
    @Mapping(target = "leagueDesc", source = "league")
    @Mapping(target = "clanWarDesc", source = "clanWar")
    @Mapping(target = "capitalDesc", source = "capital")
    public abstract BeginningMonthPlanCreateCommand toBeginningMonthPlanCreateCommand(BeginningMonthPlanCreateRequest request);

    @Named(value = "convertDate")
    protected LocalDate map(Long date) {
        return timeUtils.toLocalDate(date);
    }

}
