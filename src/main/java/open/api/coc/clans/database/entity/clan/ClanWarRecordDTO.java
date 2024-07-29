package open.api.coc.clans.database.entity.clan;

public record ClanWarRecordDTO(
    String clanTag,
    String clanName,
    String tag,
    String name,
    Long attackCount,
    Integer totalDestructionPercentage,
    Double avgDuration,
    Integer totalStars,
    Integer threeStars,
    Integer twoStars,
    Integer oneStars,
    Integer zeroStars
) {
}
