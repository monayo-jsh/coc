package open.api.coc.clans.clean.domain.beginningMonthPlan.mapper;

import java.time.LocalDate;
import open.api.coc.clans.clean.domain.beginningMonthPlan.dto.BeginningMonthCreateCommand;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthCreateRequest;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        BeginningMonthPlanMapper.class
    }
)
public abstract class BeginningMonthMapper {

    @Autowired
    private TimeUtils timeUtils;

    public abstract BeginningMonth toBeginningMonth(BeginningMonthCreateCommand createCommand);

    @Mapping(target = "month", source = "month", qualifiedByName = "convertMonthToLocalDate")
    public abstract BeginningMonthCreateCommand toBeginningMonthCommand(BeginningMonthCreateRequest request);

    @Mapping(target = "month", source = "month", qualifiedByName = "convertMonthToTimestamp")
    public abstract BeginningMonthResponse toBeginningMonthResponse(BeginningMonth beginningMonth);

    @Named(value = "convertMonthToLocalDate")
    protected LocalDate map(Long month) {
        return timeUtils.toLocalDate(month);
    }

    @Named(value = "convertMonthToTimestamp")
    protected Long map(LocalDate localDate) {
        return timeUtils.toEpochMilliSecond(localDate);
    }
}
