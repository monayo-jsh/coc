package open.api.coc.clans.clean.application.player.model;

import java.util.List;

public record PlayerSupportUpdateBulkCommand(

    // 플레이어 태그 목록
    List<String> playerTags

) {

    public boolean isRequestCompleted(long updatedSupportPlayerCount) {
        return updatedSupportPlayerCount == playerTags.size();
    }
}
