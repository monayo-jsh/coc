package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.common.YnType;

public record ClanWarMemberQuery(

    String clanTag,
    LocalDateTime preparationStartTime,
    YnType necessaryAttackYn

) {

    public static ClanWarMemberQuery create(String clanTag, LocalDateTime preparationStartTime, YnType necessaryAttackYn) {
        return new ClanWarMemberQuery(clanTag, preparationStartTime, necessaryAttackYn);
    }

}
