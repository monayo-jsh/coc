package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.common.YnType;

public record ClanWarMemberNecessaryAttackCommand(

    String clanTag,
    LocalDateTime preparationStartTime,
    String playerTag

) {

    public static ClanWarMemberNecessaryAttackCommand create(String clanTag, LocalDateTime preparationStartTime, String playerTag) {
        return new ClanWarMemberNecessaryAttackCommand(clanTag, preparationStartTime, playerTag);
    }

}
