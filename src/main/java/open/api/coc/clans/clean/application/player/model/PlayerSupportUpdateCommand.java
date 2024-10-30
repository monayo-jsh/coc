package open.api.coc.clans.clean.application.player.model;

import open.api.coc.clans.database.entity.common.YnType;

public record PlayerSupportUpdateCommand(

    // 플레이어 태그
    String playerTag,

    // 지원계정 변경 값
    YnType supportYn

) {

    public boolean isSupportPlayer() {
        return YnType.Y.equals(this.supportYn);
    }

}
