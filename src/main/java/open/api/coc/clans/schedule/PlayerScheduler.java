package open.api.coc.clans.schedule;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.service.PlayersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerScheduler {

    private final PlayersService playersService;

//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void cachingPlayers() {
//
//        log.info("start caching service [players]");
//        long before = System.nanoTime();
//        processCachingPlayers();
//        log.info("ended caching service [players]");
//        long after = System.nanoTime();
//        log.info("경과 시간 : {}", (double) (after - before) / 1_000_000_000);
//    }

//    private void processCachingPlayers() {
//
//        List<AcademeClan> clans = AcademeClan.getClanList();
//
//        for (AcademeClan clan : clans) {
//            try {
//                fetchedClanMembers(clan);
//                // clash of clan API endpoint Too Many Requests Limit 대응
//                log.debug("{} is cache completed", clan.getName());
//
//                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
//            } catch (Exception e) {
//                log.info("{} is clan search failed.. ", clan.getName());
//            }
//        }
//
//    }

//    private List<Player> fetchedClanMembers(AcademeClan clan)
//        throws ExecutionException, InterruptedException {
//        Optional<ClanMemberList> clanMembers = clanApiService.findClanMembersByClanTag(clan.getTag());
//
//        if (clanMembers.isEmpty()) {
//            log.info("{} is members search failed..", clan.getName());
//            return Collections.emptyList();
//        }
//
//
//        ClanMemberList clanMemberList = clanMembers.get();
//        ForkJoinPool forkJoinPool = new ForkJoinPool(clanMemberList.getItems().size());
//
//        return forkJoinPool.submit(() -> clanMemberList.getItems()
//                                                       .stream()
//                                                       .parallel()
//                                                       .map(this::fetchPlayerDetail))
//                           .get()
//                           .collect(Collectors.toList());
//    }

//    private Player fetchPlayerDetail(ClanMember clanMember) {
//        try {
//            Optional<Player> playerOptional = clanApiService.fetchPlayerBy(clanMember.getTag());
//            if (playerOptional.isPresent()) {
//                return playerOptional.get();
//            }
//
//            log.info("{}({}) is players search failed.. ", clanMember.getName(), clanMember.getTag());
//            return null;
//        } catch (Exception e) {
//            log.info("{}({}) is players failed.. ", clanMember.getName(), clanMember.getTag());
//            return null;
//        }
//    }


    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void syncPlayers() {

        log.info("start sync players");
        long before = System.nanoTime();
        processSyncPlayers();
        log.info("ended sync players");
        long after = System.nanoTime();
        log.info("경과 시간 : {}", (double) (after - before) / 1_000_000_000);
    }

    private void processSyncPlayers() {

        List<PlayerEntity> players = playersService.findAllPlayerEntities();

        final int offset = 50;
        for (int fromIndex = 0; fromIndex < players.size(); fromIndex += offset) {
            List<PlayerEntity> syncPlayers = players.subList(fromIndex, Math.min(fromIndex + offset, players.size()));
            syncPlayers.stream()
                       .parallel()
                       .map(player -> {
                           try {
                               return playersService.updatePlayer(player.getPlayerTag());
                           } catch (Exception e) {
                               log.error("player {} is update failed...", player.getName());
                               log.error("player {} is fail message", e.getMessage());
                               return false;
                           }
                       })
                       .collect(Collectors.toList());
        }

    }
}
