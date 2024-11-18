package open.api.coc.clans.clean.domain.clan.exception;

import java.time.LocalDateTime;
import open.api.coc.clans.common.exception.NotFoundException;

public class ClanWarNotExistsException extends NotFoundException {

    public ClanWarNotExistsException(Long warId) {
        super("클랜전 정보 없음, %s".formatted(warId));
    }

    public ClanWarNotExistsException(String clanTag, LocalDateTime startTime) {
        super("클랜전 정보 없음, %s - %s".formatted(clanTag, startTime));
    }

}
