package open.api.coc.clans.clean.domain.notice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_notice"
)
@Comment("공지사항 테이블")
public class Notice {

    @Comment("게시글 고유키")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("공지 구분")
    @Column(name = "type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @Comment("한줄 공지")
    @Column(name = "one_line", nullable = false, length = 100)
    private String oneLine;

    @Comment("게시 시작일")
    @Column(name = "posting_start_date", nullable = false)
    private LocalDateTime postingStartDate;

    @Comment("게시 종료일")
    @Column(name = "posting_end_date", nullable = false)
    private LocalDateTime postingEndDate;

    @Comment("한줄 공지")
    @Column(name = "timer_enabled", nullable = false)
    private Boolean timerEnabled;

    // 기본값 설정을 위한 빌더 객체
    public static class NoticeBuilder {
        private Boolean timerEnabled = false;
    }
}
