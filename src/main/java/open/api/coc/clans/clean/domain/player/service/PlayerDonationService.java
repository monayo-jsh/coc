package open.api.coc.clans.clean.domain.player.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.PlayerDonationStat;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.domain.player.repository.PlayerDonationRepository;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import open.api.coc.util.SeasonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerDonationService {

    private final SeasonRepository seasonRepository;
    private final PlayerDonationRepository playerDonationRepository;

    @Transactional
    public void collect(Player originPlayer, Player latestPlayer) {
        // 현재 시즌 구하기
        String season = getFormattedSeasonEndDate();

        // 플레이어 지원/지원 받은 유닛 수 수집
        PlayerDonationStat playerDonationStat = PlayerDonationStat.create(originPlayer.getTag(),
                                                                          season,
                                                                          originPlayer.getDonations(),
                                                                          originPlayer.getDonationsReceived(),
                                                                          latestPlayer.getDonations(),
                                                                          latestPlayer.getDonationsReceived(),
                                                                          latestPlayer.getDonationInfo());

        // 플레이어의 지원 통계를 조회한다.
        playerDonationRepository.findByPlayerTagAndSeason(originPlayer.getTag(), season)
                                .map(existingDonationStat -> updateDonationStat(existingDonationStat, playerDonationStat))
                                .orElseGet(() -> createDonationStat(playerDonationStat));
    }

    private PlayerDonationStat createDonationStat(PlayerDonationStat playerDonationStat) {
        return playerDonationRepository.save(playerDonationStat);
    }

    private PlayerDonationStat updateDonationStat(PlayerDonationStat originDonationStat, PlayerDonationStat newDonationStat) {
        // 기존 통계에 지원/지원 받은 유닛 수 누적
        originDonationStat.changeDonationInfo(newDonationStat);

        return playerDonationRepository.save(originDonationStat);
    }

    public LocalDate getLeagueSeasonEndDate() {
        return getLeagueSeasonEndDateTime().toLocalDate();
    }

    public String getFormattedSeasonEndDate() {
        LocalDateTime seasonEndDate = getLeagueSeasonEndDateTime();
        return formatLeagueSeason(seasonEndDate.toLocalDate());
    }

    private String formatLeagueSeason(LocalDate seasonEndDate) {
        return seasonEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public LocalDateTime getLeagueSeasonEndDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime seasonEndTime = SeasonUtils.getSeasonEndTime(now.getYear(), now.getMonthValue());

        LocalDate seasonEndDate = seasonRepository.findSeasonEndDateByBaseDate(now.toLocalDate()).orElse(null);
        if (seasonEndDate != null) {
            // 시즌 종료일이 설정된 경우 설정값으로 적용
            seasonEndTime = SeasonUtils.withSeasonEndTime(seasonEndDate);
        }

        if (!now.isBefore(seasonEndTime)) {
            // 현재 시간 기준으로 이번달 시즌 종료 이후에는 다음달 시즌 종료일로 조정
            seasonEndTime = seasonEndTime.plusMonths(1);
        }

        return seasonEndTime;
    }

    @Transactional(readOnly = true)
    public List<PlayerDonationDTO> findDonationRanking(LocalDate seasonEndDate, Integer pageSize) {
        if (Objects.isNull(seasonEndDate)) {
            throw new IllegalArgumentException("season is not empty");
        }

        String leagueSeason = formatLeagueSeason(seasonEndDate);
        return playerDonationRepository.findDonationRanking(leagueSeason, pageSize);
    }

    @Transactional(readOnly = true)
    public List<PlayerDonationDTO> findDonationReceivedRanking(LocalDate seasonEndDate, Integer pageSize) {
        if (Objects.isNull(seasonEndDate)) {
            throw new IllegalArgumentException("season is not empty");
        }

        String leagueSeason = formatLeagueSeason(seasonEndDate);
        return playerDonationRepository.findDonationReceivedRanking(leagueSeason, pageSize);
    }

}
