package open.api.coc.clans.clean.domain.clan.event;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.service.ClanAssignService;
import open.api.coc.clans.clean.domain.player.event.SupportPlayerChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportPlayerChangedEventHandler {

    private final ClanAssignService assignService;

    @EventListener(SupportPlayerChangedEvent.class)
    public void handle(SupportPlayerChangedEvent event) {
        assignService.excludeRecently(event.playerTag());
    }
}
