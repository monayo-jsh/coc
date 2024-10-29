package open.api.coc.clans.clean.domain.clan.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanAssignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanAssignService {

    private final ClanAssignRepository assignRepository;

    @Transactional
    public void excludeRecently(String playerTag) {
        String latestSeasonDate = assignRepository.findLatestSeasonDate();
        if (Objects.isNull(latestSeasonDate)) return;

        assignRepository.cancel(latestSeasonDate, playerTag);
    }

    @Transactional
    public void excludeRecently(List<String> playerTags) {
        String latestSeasonDate = assignRepository.findLatestSeasonDate();
        if (Objects.isNull(latestSeasonDate)) return;

        for (String playerTag : playerTags) {
            assignRepository.cancel(latestSeasonDate, playerTag);
        }
    }
}
