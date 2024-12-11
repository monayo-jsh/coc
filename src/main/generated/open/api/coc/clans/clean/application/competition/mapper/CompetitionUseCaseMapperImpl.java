package open.api.coc.clans.clean.application.competition.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionCreateRequest;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-12T13:59:00+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class CompetitionUseCaseMapperImpl implements CompetitionUseCaseMapper {

    @Override
    public CompetitionCreateCommand toCreateCommand(CompetitionCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        String name = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String discordUrl = null;
        String ruleBookUrl = null;
        Integer roasterSize = null;
        String restrictions = null;
        String remarks = null;

        name = request.name();
        startDate = request.startDate();
        endDate = request.endDate();
        discordUrl = request.discordUrl();
        ruleBookUrl = request.ruleBookUrl();
        roasterSize = request.roasterSize();
        restrictions = request.restrictions();
        remarks = request.remarks();

        CompetitionCreateCommand competitionCreateCommand = new CompetitionCreateCommand( name, startDate, endDate, discordUrl, ruleBookUrl, roasterSize, restrictions, remarks );

        return competitionCreateCommand;
    }

    @Override
    public CompetitionResponse toResponse(Competition competition) {
        if ( competition == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String discordUrl = null;
        String ruleBookUrl = null;
        Integer roasterSize = null;
        String restrictions = null;
        String remarks = null;

        CompetitionResponse competitionResponse = new CompetitionResponse( id, name, startDate, endDate, discordUrl, ruleBookUrl, roasterSize, restrictions, remarks );

        return competitionResponse;
    }
}
