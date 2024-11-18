package open.api.coc.clans.clean.application.clan.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import open.api.coc.clans.database.entity.common.YnType;

public record ClanWarMemberQuery(

    String clanTag,
    LocalDateTime startTime,
    YnType necessaryAttackYn

) {

    public static ClanWarMemberQuery create(String clanTag, LocalDateTime startTime, YnType necessaryAttackYn) {
        return new ClanWarMemberQuery(clanTag, startTime, necessaryAttackYn);
    }

}
