package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanGame;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanGameRepository;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanGameService {

    private final ClanGameRepository clanGameRepository;

    private static final DateTimeFormatter PROGRESS_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    @Transactional
    public void collect(Player player) {
        String currentProgressDate = currentProgressDate();

        clanGameRepository.findByPlayerTagAndProgressDate(player.getTag(), currentProgressDate)
                          .map((clanGame) -> updateCurrentSeasonClanGame(clanGame, player))
                          .orElseGet(() -> createNewSeasonClanGame(player, currentProgressDate));
    }

    private ClanGame createNewSeasonClanGame(Player player, String newSeasonProgressDate) {
        ClanGame newClanGame = ClanGame.create(player.getTag(), newSeasonProgressDate, player.getClanGamePoint());
        return clanGameRepository.save(newClanGame);
    }

    private ClanGame updateCurrentSeasonClanGame(ClanGame clanGame, Player player) {
        clanGame.changeFinishPoint(player.getClanGamePoint());
        return clanGameRepository.save(clanGame);
    }

    public List<ClanGameDTO> getLatestClanGames() {
        return clanGameRepository.findAllByProgressDate(currentProgressDate())
                                 .stream()
                                 .sorted(Comparator.comparing(ClanGameDTO::getPoint).reversed()
                                                   .thenComparing(ClanGameDTO::getLastModifiedAt))
                                 .collect(Collectors.toList());
    }

    private String currentProgressDate() {
        return LocalDate.now().format(PROGRESS_DATE_FORMATTER);
    }
}
