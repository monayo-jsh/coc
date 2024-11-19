package open.api.coc.clans.clean.domain.clan.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class ClanWarMemberNotExistsException extends NotFoundException {

    public ClanWarMemberNotExistsException(Long warId, String playerTag) {
        super("클랜전 참여 정보 없음, %s - %s".formatted(warId, playerTag));
    }

}
