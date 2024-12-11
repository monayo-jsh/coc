package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;

public interface PlayerSupportRepository {

    void resetAll();

    long update(List<String> playerTags);

}
