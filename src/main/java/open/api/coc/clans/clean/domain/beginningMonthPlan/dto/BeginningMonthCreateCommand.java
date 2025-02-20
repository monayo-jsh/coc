package open.api.coc.clans.clean.domain.beginningMonthPlan.dto;

import java.time.LocalDate;
import java.util.List;

public record BeginningMonthCreateCommand(

    LocalDate month,

    List<BeginningMonthPlanCreateCommand> plans

) {
}
