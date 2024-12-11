package open.api.coc.clans.clean.domain.notice.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class NoticeNotFoundException extends NotFoundException {

    public NoticeNotFoundException(Long noticeId) {
        super("공지사항(%s) 정보 없음".formatted(noticeId));
    }
}
