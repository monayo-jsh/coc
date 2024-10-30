package open.api.coc.clans.clean.domain.player.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerLegendRecordService {

    private final SeasonRepository seasonRepository;
    private final PlayerRecordRepository playerRecordRepository;

    @Transactional(readOnly = true)
    public List<PlayerRecordHistory> findAllLatest(String playerTag) {
        // 최근 시즌 종료일 2건을 가져온다.
        List<LocalDate> seasonEndDates = seasonRepository.findLatestSeasonEndDate(2);

        // 최근 시즌 종료일 데이터가 2건 미만인 경우 미제공
        if (seasonEndDates.size() < 2) return Collections.emptyList();

        // 전설 리그 시즌 시간 설정
        LocalTime seasonTime = LocalTime.of(14, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(seasonEndDates.get(0), seasonTime);
        LocalDateTime startDateTime = LocalDateTime.of(seasonEndDates.get(1), seasonTime);

        return playerRecordRepository.findAll(playerTag, startDateTime, endDateTime);
    }

    @Transactional
    public void createHistory(Player originPlayer, Player latestPlayer) {
        // 기록 대상이 아닌 경우 기록하지 않음
        if (latestPlayer.isNotRecoding(originPlayer)) return;

        // 기록 대상 플레이어만 기록한다.
        if (!playerRecordRepository.existsByTag(originPlayer.getTag())) {
            return;
        }

        // 플레이어 기록 생성
        PlayerRecordHistory recordHistory = PlayerRecordHistory.builder()
                                                               .tag(originPlayer.getTag())
                                                               .oldTrophies(latestPlayer.getTrophies())
                                                               .oldAttackWins(latestPlayer.getAttackWins())
                                                               .oldDefenceWins(latestPlayer.getDefenseWins())
                                                               .newTrophies(originPlayer.getTrophies())
                                                               .newAttackWins(originPlayer.getAttackWins())
                                                               .newDefenceWins(originPlayer.getDefenseWins())
                                                               .build();

        playerRecordRepository.saveHistory(recordHistory);
    }

}
