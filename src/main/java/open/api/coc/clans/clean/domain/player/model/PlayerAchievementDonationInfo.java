package open.api.coc.clans.clean.domain.player.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerAchievementDonationInfo {

    private Integer troopCount; // 유닛
    private Integer spellCount; // 마법
    private Integer siegeMachineCount; // 시즈머신

    public static PlayerAchievementDonationInfo create(Integer donationTroopCount, Integer donationSpellCount, Integer donationSiegeMachineCount) {
        return new PlayerAchievementDonationInfo(donationTroopCount, donationSpellCount, donationSiegeMachineCount);
    }
}
