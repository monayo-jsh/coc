package open.api.coc.clans.clean.domain.clan.model;

public record ClanWarParticipationStatusRecordDTO(
    String playerTag,
    String playerName,

    Integer clanWarCount,
    Integer parallelWarCount,
    Integer leagueWarCount,

    Long totalParticipationCount
) {
}
