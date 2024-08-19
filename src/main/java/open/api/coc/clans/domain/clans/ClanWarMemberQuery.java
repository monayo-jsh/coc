package open.api.coc.clans.domain.clans;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.util.StringUtils;

@Getter
public class ClanWarMemberQuery {

    private final String clanTag;
    private final LocalDateTime startTime;
    private final YnType necessaryAttackYn;

    private ClanWarMemberQuery(String clanTag, LocalDateTime startTime, YnType necessaryAttackYn) {
        this.clanTag = clanTag;
        this.startTime = startTime;
        this.necessaryAttackYn = necessaryAttackYn;
    }

    public static ClanWarMemberQuery create(String clanTag, LocalDateTime startTime, String necessaryAttackYn) {
        YnType convNecessaryAttackYn = null;
        if (StringUtils.hasText(necessaryAttackYn)) {
            try {
                convNecessaryAttackYn = YnType.valueOf(necessaryAttackYn);
            } catch (Exception ignored) {}
        }

        return new ClanWarMemberQuery(clanTag, startTime, convNecessaryAttackYn);
    }

    public boolean isConditionWithNecessaryAttackYn() {
        return Objects.nonNull(necessaryAttackYn);
    }
}
