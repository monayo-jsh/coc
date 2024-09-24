package open.api.coc.clans.common.runner;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.infrastructure.league.entity.LeagueEntity;
import open.api.coc.clans.clean.infrastructure.league.mapper.LeagueEntityMapper;
import open.api.coc.clans.clean.infrastructure.league.repository.JpaLeagueRepository;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetaRunner implements CommandLineRunner {

    private final ClanApiService clanApiService;

    private final JpaLeagueRepository jpaLeagueRepository;
    private final LeagueEntityMapper leagueEntityMapper;

    @Override
    public void run(String... args) {
        Optional<LabelList> findLeagues = clanApiService.findLeagues();
        if (findLeagues.isEmpty()) {
            log.info("leagues is empty ...");
            return;
        }

        LabelList leagues = findLeagues.get();

        List<LeagueEntity> leagueEntities = leagues.getItems()
                                                   .stream()
                                                   .map(league -> {
                                                       LeagueEntity leagueEntity = leagueEntityMapper.toEntity(league);
                                                       leagueEntity.markedNotNew();
                                                       return leagueEntity;
                                                   })
                                                   .toList();

        jpaLeagueRepository.saveAll(leagueEntities);
    }
}
