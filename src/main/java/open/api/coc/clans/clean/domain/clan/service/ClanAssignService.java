package open.api.coc.clans.clean.domain.clan.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanAssignedPlayer;
import open.api.coc.clans.clean.domain.clan.repository.ClanAssignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanAssignService {

    private final ClanAssignRepository assignRepository;

    @Transactional(readOnly = true)
    public String findLatestAssignedMonth() {
        return assignRepository.findLatestAssignedMonth();
    }

    @Transactional
    public void excludeRecently(String playerTag) {
        String latestSeasonDate = findLatestAssignedMonth();

        assignRepository.cancel(latestSeasonDate, playerTag);
    }

    @Transactional
    public void excludeRecently(List<String> playerTags) {
        String latestSeasonDate = findLatestAssignedMonth();

        for (String playerTag : playerTags) {
            assignRepository.cancel(latestSeasonDate, playerTag);
        }
    }

    public List<ClanAssignedPlayer> findAll(String assignedDate, String clanTag) {
        return assignRepository.findAll(assignedDate, clanTag);
    }
}
