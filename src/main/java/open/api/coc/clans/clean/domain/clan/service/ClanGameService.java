package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.mapper.ClanGameMapper;
import open.api.coc.clans.clean.domain.clan.model.ClanGame;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanGameMeta;
import open.api.coc.clans.clean.domain.clan.repository.ClanGameRepository;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.presentation.clan.dto.game.ClanGameResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanGameService {

    private final ClanGameRepository clanGameRepository;

    private final ClanGameMapper clanGameMapper;
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

    public ClanGameResponse getLatestClanGame() {
        // 현재 진행월 구하기
        String progressDate = currentProgressDate();

        // 클랜 게임 완료 점수 구하기
        Integer completedPoint = getCompletedPointByProgressDate(progressDate);

        // 클랜 게임 진행 현황 조회
        List<ClanGameDTO> clanGames = clanGameRepository.findAllByProgressDate(progressDate)
                                                        .stream()
                                                        .sorted(Comparator.comparing(ClanGameDTO::getPoint).reversed()
                                                                          .thenComparing(ClanGameDTO::getLastModifiedAt)
                                                                          .thenComparing(ClanGameDTO::getPlayerName))
                                                        .collect(Collectors.toList());

        return clanGameMapper.toClanGameResponse(progressDate, completedPoint, clanGames);
    }

    private Integer getCompletedPointByProgressDate(String progressDate) {
        final Integer completedPoint = 4000;
        return clanGameRepository.findClanGameMetaByProgressDate(progressDate)
                                 .map(ClanGameMeta::getCompletedPoint)
                                 .orElse(completedPoint);
    }

    private String currentProgressDate() {
        return LocalDate.now().format(PROGRESS_DATE_FORMATTER);
    }
}
