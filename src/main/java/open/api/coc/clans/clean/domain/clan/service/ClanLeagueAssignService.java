package open.api.coc.clans.clean.domain.clan.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanLeagueAssignRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanLeagueAssignService {

    private final ClanLeagueAssignRepository assignRepository;

    public void cancelRecently(String playerTag) {
        String latestSeasonDate = assignRepository.findLatestSeasonDate();
        if (Objects.isNull(latestSeasonDate)) return;

        assignRepository.cancel(latestSeasonDate, playerTag);
    }
}
