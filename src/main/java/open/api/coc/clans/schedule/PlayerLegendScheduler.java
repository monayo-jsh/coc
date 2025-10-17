package open.api.coc.clans.schedule;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.player.PlayerUseCase;
import open.api.coc.clans.schedule.handler.CollectionHandler;
import open.api.coc.clans.service.PlayersService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduling.collect.player.legend.enabled", havingValue = "true", matchIfMissing = false)
public class PlayerLegendScheduler {

    private final CollectionHandler collectionHandler;

    private final PlayersService playersService;
    private final PlayerUseCase playerUseCase;


    @Scheduled(fixedDelayString = "${scheduling.collect.player.legend.record-milli-sec}")  // 매 10초마다
    public void processForPlayerRecordKeeping() {
        if (collectionHandler.isNotCollectionTime()) {
            return;
        }

        List<String> playerTags = playersService.findAllPlayersToRecord();
        if (playerTags.isEmpty()) return;
        for(String playerTag : playerTags) {
            playerUseCase.synchronizePlayerFromSchedule("processForPlayerRecordKeeping", playerTag);
        }
    }

}
