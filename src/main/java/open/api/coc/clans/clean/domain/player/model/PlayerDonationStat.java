package open.api.coc.clans.clean.domain.player.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class PlayerDonationStat {

    private Long id; // 통계 고유키
    private String playerTag; // 플레이어 태그

    private String season; // 시즌

    private int donationsDelta; // 누적 지원수

    private int donationsReceivedDelta; // 누적 지원받은수

    private Long version; // 행 버전값

    @Builder
    private PlayerDonationStat(Long id, String playerTag, String season, int donationsDelta, int donationsReceivedDelta, Long version) {
        this.id = id;
        this.playerTag = playerTag;
        this.season = season;
        this.donationsDelta = donationsDelta;
        this.donationsReceivedDelta = donationsReceivedDelta;
        this.version = version;
    }

    public static PlayerDonationStat create(String playerTag, String season, Integer lastDonations, Integer lastDonationsReceived, Integer currentDonations, Integer currentDonationsReceived) {
        int donationsDelta = calculateDelta(lastDonations, currentDonations);
        int donationsReceivedDelta = calculateDelta(lastDonationsReceived, currentDonationsReceived);

        return new PlayerDonationStat(null, playerTag, season, donationsDelta, donationsReceivedDelta, null);
    }

    private static int calculateDelta(int lastValue, int currentValue) {
        return (currentValue == 0) ? 0 : currentValue - lastValue;
    }

    public void addDonationsDelta(int donationsDelta) {
        this.donationsDelta += donationsDelta;
    }

    public void addDonationsReceivedDelta(int donationsReceivedDelta) {
        this.donationsReceivedDelta += donationsReceivedDelta;
    }

}
