package open.api.coc.clans.clean.presentation.competition;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.competition.CompetitionUseCase;
import open.api.coc.clans.clean.application.competition.mapper.CompetitionUseCaseMapper;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionCreateRequest;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competition")
public class CompetitionController {

    private final CompetitionUseCaseMapper competitionUseCaseMapper;
    private final CompetitionUseCase competitionUseCase;

    @PostMapping("")
    public ResponseEntity<CompetitionResponse> postCompetition(@Valid @RequestBody CompetitionCreateRequest request) {

        CompetitionCreateCommand command = competitionUseCaseMapper.toCreateCommand(request);
        CompetitionResponse competition = competitionUseCase.create(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(competition);
    }


}
