package open.api.coc.external.coc.clan.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementProgressResponse {

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
