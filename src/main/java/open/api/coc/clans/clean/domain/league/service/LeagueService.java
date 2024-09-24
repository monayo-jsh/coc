package open.api.coc.clans.clean.domain.league.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.league.mapper.LeagueMapper;
import open.api.coc.clans.clean.domain.league.repository.LeagueRepository;
import open.api.coc.clans.clean.presentation.league.dto.LeagueResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;

    private final LeagueMapper leagueMapper;

    public List<LeagueResponse> getLeagues() {
        return leagueRepository.findAll()
                               .stream()
                               .map(leagueMapper::toResponse)
                               .toList();

    }

}
