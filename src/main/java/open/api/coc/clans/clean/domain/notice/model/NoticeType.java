package open.api.coc.clans.clean.domain.notice.model;

import lombok.Getter;

@Getter
public enum NoticeType {

    NOTICE("공지"),
    EVENT("이벤트"),
    COUPON("쿠폰");

    private final String name;

    NoticeType(String name) {
        this.name = name;
    }
}
