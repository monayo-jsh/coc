package open.api.coc.clans.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import open.api.coc.clans.clean.infrastructure.league.persistence.repository.JpaLeagueRepository;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.clans.domain.clans.converter.LabelResponseConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaguesService {

    private final JpaLeagueRepository leagueRepository;
    private final LabelResponseConverter labelResponseConverter;

    public List<LabelResponse> getLeagues() {
        List<LeagueEntity> leagueEntities = leagueRepository.findAll();

        return leagueEntities.stream()
                             .map(labelResponseConverter::convert)
                             .collect(Collectors.toList());
    }
}
