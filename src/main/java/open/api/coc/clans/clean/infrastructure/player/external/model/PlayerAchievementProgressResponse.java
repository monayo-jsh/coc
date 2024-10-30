package open.api.coc.clans.clean.infrastructure.player.external.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAchievementProgressResponse {

    // 수집 대상
    // 지원한 유닛: Friend in Need, Sharing is caring, Siege Sharer
    // 클랜 게임: Games Champion
    private String name;
    private Integer value;

    public boolean isClanGameData() {
        return "Games Champion".equals(this.name);
    }

    public boolean isDonationTroops() {
        return "Friend in Need".equals(this.name);
    }

    public boolean isDonationSpell() {
        return "Sharing is caring".equals(this.name);
    }

    public boolean isDonationSiege() {
        return "Siege Sharer".equals(this.name);
    }

}
