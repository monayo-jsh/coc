package open.api.coc.clans.domain.players;

public record PlayerRecordResponse(

    String tag,
    String name,
    Integer oldTrophies,
    Integer newTrophies,
    Integer diffTrophies

) {

    public PlayerRecordResponse(String tag, String name, Integer oldTrophies, Integer newTrophies) {
        this(tag, name, oldTrophies, newTrophies, newTrophies - oldTrophies);
    }
}
