package open.api.coc.clans.domain.clans;

import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;

public record ClanWarMissingAttackPlayerDTO(String clanName, Long warId, ClanWarType warType,
                                            String warState, LocalDateTime startTime,
                                            String playerTag, String playerName) {
}
