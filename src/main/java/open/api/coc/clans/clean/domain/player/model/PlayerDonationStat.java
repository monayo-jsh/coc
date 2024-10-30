package open.api.coc.clans.clean.domain.player.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerDonationStat {

    private Long id; // 통계 고유키
    private String playerTag; // 플레이어 태그

    private String season; // 시즌

    private int donationsDelta; // 누적 지원수
    private int donationsReceivedDelta; // 누적 지원받은수

    private int oldDonationTroops; // 시즌 시작 지원 유닛수
    private int newDonationTroops; // 시즌 변화 지원 유닛수

    private int oldDonationSpells; // 시즌 시작 지원 마법수
    private int newDonationSpells; // 시즌 변화 지원 마법수

    private int oldDonationSieges; // 시즌 시작 지원 시즈머신수
    private int newDonationSieges; // 시즌 변화 지원 시즈머신수

    private Long version; // 행 버전값

    public static PlayerDonationStat create(String playerTag, String season, Integer lastDonations, Integer lastDonationsReceived,
                                            Integer currentDonations, Integer currentDonationsReceived,
                                            PlayerAchievementDonationInfo donationInfo) {

        int donationsDelta = calculateDelta(lastDonations, currentDonations);
        int donationsReceivedDelta = calculateDelta(lastDonationsReceived, currentDonationsReceived);

        return new PlayerDonationStat(null, playerTag, season, donationsDelta, donationsReceivedDelta,
                                      donationInfo.getTroopCount(), donationInfo.getTroopCount(),
                                      donationInfo.getSpellCount(), donationInfo.getSpellCount(),
                                      donationInfo.getSiegeMachineCount(), donationInfo.getSiegeMachineCount(),
                                      null);
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

    public void changeDonationInfo(PlayerDonationStat newDonationStat) {
        // 지원유닛수, 지원받은유닛수 추적
        addDonationsDelta(newDonationStat.getDonationsDelta());
        addDonationsReceivedDelta(newDonationStat.getDonationsReceivedDelta());

        // 업적 지원 관련 정보 갱신
        this.newDonationTroops = newDonationStat.getNewDonationTroops();
        this.newDonationSpells = newDonationStat.getNewDonationSpells();
        this.newDonationSieges = newDonationStat.getNewDonationSieges();
    }
}
