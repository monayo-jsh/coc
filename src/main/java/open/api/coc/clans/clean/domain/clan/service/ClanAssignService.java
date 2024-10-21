package open.api.coc.clans.clean.domain.clan.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanAssignRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanAssignService {

    private final ClanAssignRepository assignRepository;

    public void excludeRecently(String playerTag) {
        String latestSeasonDate = assignRepository.findLatestSeasonDate();
        if (Objects.isNull(latestSeasonDate)) return;

        assignRepository.cancel(latestSeasonDate, playerTag);
    }
}
