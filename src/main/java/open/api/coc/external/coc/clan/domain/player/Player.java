package open.api.coc.external.coc.clan.domain.player;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.clan.domain.common.Label;
import open.api.coc.external.coc.clan.domain.common.PlayerClan;
import open.api.coc.external.coc.clan.domain.common.Troops;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private String name;
    private String tag;
    private Integer expLevel;

    private Integer townHallLevel;

    private Integer trophies;
    private Integer bestTrophies;

    private Integer donations;
    private Integer donationsReceived;

    private Integer attackWins;
    private Integer defenseWins;

    private Integer warStars;

    private Label league;

    private String role;
    private String warPreference;
    private PlayerClan clan;

    private List<Hero> heroes;
    private List<HeroEquipment> heroEquipment;

    private List<Troops> troops;
    private List<Troops> spells;

    private List<AchievementProgressResponse> achievements;

    public boolean isRecoding(PlayerEntity playerEntity) {
        // 공격 성공 수치 다른 경우 기록
        if (!Objects.equals(this.attackWins, playerEntity.getAttackWins())) return true;

        // 방어 성공 수치 다른 경우 기록
        if (!Objects.equals(this.defenseWins, playerEntity.getDefenseWins())) return true;

        // 트로피 점수 다른 경우 기록
        if (!Objects.equals(this.trophies, playerEntity.getTrophies())) return true;

        // 그 외는 기록하지 않음
        return false;
    }

    public boolean isNotRecoding(PlayerEntity playerEntity) {
        return !isRecoding(playerEntity);
    }

    public Integer getClanGamePoint() {
        return achievements.stream()
                           .filter(AchievementProgressResponse::isClanGameData)
                           .findFirst()
                           .map(AchievementProgressResponse::getValue)
                           .orElseThrow(() -> new CustomRuntimeException(ExceptionCode.EXTERNAL_ERROR.getCode(), "클랜 게임 데이터 찾지 못함")); // 리팩토링 시 예외 객체 수정

    }

    public Integer getDonationTroops() {
        return achievements.stream()
                           .filter(AchievementProgressResponse::isDonationTroops)
                           .findFirst()
                           .map(AchievementProgressResponse::getValue)
                           .orElse(0);
    }

    public Integer getDonationSpell() {
        return achievements.stream()
                           .filter(AchievementProgressResponse::isDonationSpell)
                           .findFirst()
                           .map(AchievementProgressResponse::getValue)
                           .orElse(0);
    }

    public Integer getDonationSiege() {
        return achievements.stream()
                           .filter(AchievementProgressResponse::isDonationSiege)
                           .findFirst()
                           .map(AchievementProgressResponse::getValue)
                           .orElse(0);
    }
}
