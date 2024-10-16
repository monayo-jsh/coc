package open.api.coc.clans.clean.domain.player.service;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerRecordService {

    private final PlayerRecordRepository playerRecordRepository;

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
