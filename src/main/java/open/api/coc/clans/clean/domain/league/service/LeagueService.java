package open.api.coc.clans.clean.domain.league.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.league.mapper.LeagueMapper;
import open.api.coc.clans.clean.domain.league.model.League;
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

    public Map<Integer, League> findAllMapByIds(List<Integer> leagueIds) {
        if (leagueIds == null || leagueIds.isEmpty()) {
            throw new IllegalArgumentException("leagueIds can not be null or empty");
        }

        return leagueRepository.findAllByIds(leagueIds)
                               .stream()
                               .collect(Collectors.toMap(League::getId, league -> league));
    }
}
