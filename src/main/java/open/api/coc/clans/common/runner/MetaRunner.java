package open.api.coc.clans.common.runner;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.database.entity.league.LeagueEntity;
import open.api.coc.clans.database.entity.league.converter.LeagueEntityConverter;
import open.api.coc.clans.database.repository.common.LeagueRepository;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetaRunner implements CommandLineRunner {

    private final ClanApiService clanApiService;
    private final LeagueRepository leagueRepository;
    private final LeagueEntityConverter leagueEntityConverter;
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
                                                       LeagueEntity leagueEntity = leagueEntityConverter.convert(league);
                                                       leagueEntity.setNew(false);
                                                       return leagueEntity;
                                                   })
                                                   .toList();

        leagueRepository.saveAll(leagueEntities);
    }
}
