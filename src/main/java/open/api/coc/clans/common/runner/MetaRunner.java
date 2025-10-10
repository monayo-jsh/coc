package open.api.coc.clans.common.runner;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import open.api.coc.clans.clean.infrastructure.league.persistence.mapper.LeagueEntityMapper;
import open.api.coc.clans.clean.infrastructure.league.persistence.repository.JpaLeagueRepository;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import open.api.coc.external.coc.league.LeagueApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetaRunner implements CommandLineRunner {

    private final LeagueApi leagueApi;

    private final JpaLeagueRepository jpaLeagueRepository;
    private final LeagueEntityMapper leagueEntityMapper;

    @Override
    public void run(String... args) {
        collectLeagues();
        collectLeagueTiers();
    }

    private void collectLeagues() {
        Optional<LabelList> findLeagues = leagueApi.findLeagues();
        if (findLeagues.isEmpty()) {
            log.info("leagues is empty ...");
            return;
        }

        save(findLeagues);
    }

    private void collectLeagueTiers() {
        Optional<LabelList> findLeagueTiers = leagueApi.findLeagueTiers();
        if (findLeagueTiers.isEmpty()) {
            log.info("leagueTiers is empty ...");
            return;
        }

        save(findLeagueTiers);
    }

    private void save(Optional<LabelList> labels) {
        if (labels.isEmpty()) {
            log.info("labels is empty ...");
            return;
        }
        LabelList leagueTiers = labels.get();

        List<LeagueEntity> leagueEntities = leagueTiers.getItems()
                                                       .stream()
                                                       .map(league -> {
                                                           LeagueEntity leagueEntity = leagueEntityMapper.toLeagueEntity(league);
                                                           leagueEntity.markedNotNew();
                                                           return leagueEntity;
                                                       })
                                                       .toList();

        jpaLeagueRepository.saveAll(leagueEntities);
    }
}
