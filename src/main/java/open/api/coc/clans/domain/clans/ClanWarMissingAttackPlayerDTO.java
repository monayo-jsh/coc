package open.api.coc.clans.domain.clans;

import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.entity.common.YnType;

public record ClanWarMissingAttackPlayerDTO(String clanName, Integer clanOrder,
                                            Long warId, ClanWarType warType,
                                            String warState,
                                            LocalDateTime startTime,
                                            LocalDateTime endTime,
                                            String playerTag, String playerName,
                                            YnType necessaryAttackYn) {
}
