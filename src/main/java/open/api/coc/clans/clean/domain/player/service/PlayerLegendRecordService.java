package open.api.coc.clans.clean.domain.player.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.league.repository.LeagueRepository;
import open.api.coc.clans.clean.domain.player.exception.PlayerNotLeagueException;
import open.api.coc.clans.clean.domain.player.exception.PlayerNotLegendLeagueException;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordHistoryRepository;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordPK;
import open.api.coc.clans.schedule.handler.CollectionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PlayerLegendRecordService {

    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final PlayerRecordRepository recordRepository;
    private final PlayerRecordHistoryRepository recordHistoryRepository;

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

        return recordHistoryRepository.findAll(playerTag, startDateTime, endDateTime);
    }

    @Transactional
    public void createHistoryIfNotLegendLeagueExcept(Player originPlayer, Player latestPlayer) {

        String baseSeason = getBaseSeason();

        PlayerRecordPK playerRecordId = PlayerRecordPK.create(originPlayer.getTag(), baseSeason);

        if (isNotRecording(originPlayer, latestPlayer)) return;
        if (latestPlayer.isNotInLeague()) {
            // 리그 배정 안된 상태면 기록하지 않음 (시즌 초기화 등..)
            recordRepository.deleteById(playerRecordId);
            return;
        }
        if (latestPlayer.isNotInLegend()) {
            // 전설 리그 아닌 경우 기록하지 않음
            recordRepository.deleteById(playerRecordId);
            return;
        }

        recordSeasonTrophies(playerRecordId, latestPlayer);
        createRecordHistory(originPlayer, latestPlayer);
    }

    private String getBaseSeason() {
        LocalDate latestSeasonEndDate = null;
        List<LocalDate> latestSeasonEndDates = seasonRepository.findLatestSeasonEndDate(1);
        if (!latestSeasonEndDates.isEmpty()) {
            latestSeasonEndDate = latestSeasonEndDates.get(0);
        }

        return CollectionHandler.getSeason(latestSeasonEndDate);
    }

    private void recordSeasonTrophies(PlayerRecordPK playerRecordId, Player latestPlayer) {

        Optional<PlayerRecordEntity> findPlayerSeasonRecord = recordRepository.findById(playerRecordId);

        if (findPlayerSeasonRecord.isPresent()) {
            PlayerRecordEntity updatePlayerRecord = findPlayerSeasonRecord.get();
            updatePlayerRecord.changeTrophies(latestPlayer.getTrophies());
            recordRepository.save(updatePlayerRecord);
            return;
        }

        PlayerRecordEntity newPlayerRecord = PlayerRecordEntity.create(playerRecordId, latestPlayer.getTrophies());
        recordRepository.save(newPlayerRecord);
    }

    private boolean isNotRecording(Player originPlayer, Player latestPlayer) {
        // 기록 대상 아닌 경우
        return !latestPlayer.isRecoding(originPlayer);
    }

    private boolean isNotLegendLeague(Player latestPlayer) {
        // 레전드 리그여부 검증
        Optional<League> findLeague = leagueRepository.findById(latestPlayer.getLeagueId());
        return findLeague.isEmpty() || !findLeague.get().isLegend();
    }

    private void createRecordHistory(Player originPlayer, Player latestPlayer) {
        // 플레이어 기록 생성
        PlayerRecordHistory recordHistory = PlayerRecordHistory.builder()
                                                               .tag(originPlayer.getTag())
                                                               .oldTrophies(originPlayer.getTrophies())
                                                               .oldAttackWins(originPlayer.getAttackWins())
                                                               .oldDefenceWins(originPlayer.getDefenseWins())
                                                               .newTrophies(latestPlayer.getTrophies())
                                                               .newAttackWins(latestPlayer.getAttackWins())
                                                               .newDefenceWins(latestPlayer.getDefenseWins())
                                                               .build();

        recordHistoryRepository.save(recordHistory);
    }

    @Transactional
    public void registerCollectionTarget(Player player) {
        if (player.isNotInLeague()) {
            throw new PlayerNotLeagueException(player.getTag());
        }

        if (isNotLegendLeague(player)) {
            throw new PlayerNotLegendLeagueException();
        }

//        if (recordRepository.existsByTag(player.getTag())) {
//            throw new PlayerAlreadyExistsException(player.getTag());
//        }

        PlayerRecordEntity playerRecordEntity = PlayerRecordEntity.builder().id(PlayerRecordPK.builder().tag(player.getTag()).build()).build();
        recordRepository.save(playerRecordEntity);
    }

    @Transactional
    public void delete(String playerTag) {
//        recordRepository.deleteById(playerTag);
    }

    public List<PlayerLegendRecordTargetDTO> findAllBySeason(String season) {
        return recordRepository.findAllBySeason(season);
    }

    public List<PlayerLegendRecordTargetDTO> findAllTagByName(String name) {
        if (!StringUtils.hasText(name)) return Collections.emptyList();

        return recordRepository.findAllByNameOrNickname(name);
    }

//    public PlayerRecordEntity findByTagOrThrow(String tag) {
//        return recordRepository.findById(tag)
//                               .orElseThrow(() -> new PlayerNotFoundException(tag));
//    }

    public void save(PlayerRecordEntity playerRecord) {
        recordRepository.save(playerRecord);
    }

}
