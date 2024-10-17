package open.api.coc.clans.clean.domain.clan.repository;

public interface ClanLeagueAssignRepository {

    String findLatestSeasonDate();
    void cancel(String seasonDate, String playerTag);

}
