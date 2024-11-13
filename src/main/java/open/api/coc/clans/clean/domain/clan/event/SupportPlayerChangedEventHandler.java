package open.api.coc.clans.clean.domain.clan.event;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.service.ClanAssignService;
import open.api.coc.clans.clean.domain.player.event.SupportPlayerChangedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class SupportPlayerChangedEventHandler {

    private final ClanAssignService assignService;

    @TransactionalEventListener(
        classes = SupportPlayerChangedEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(SupportPlayerChangedEvent event) {
        assignService.excludeRecently(event.playerTag());
    }
}
