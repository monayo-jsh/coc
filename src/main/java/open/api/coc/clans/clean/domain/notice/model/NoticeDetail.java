package open.api.coc.clans.clean.domain.notice.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_notice_detail"
)
@Comment("공지사항 테이블")
public class NoticeDetail {

    @Comment("게시글 상세 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("게시글 참조")
    @OneToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "notice_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Notice notice;

    @Comment("상세 내용")
    @Lob
    @Column(name = "content", nullable = true)
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private NoticeDetail(Long id, Notice notice, String content) {
        this.id = id;
        this.notice = notice;
        this.content = content;
    }

    public static NoticeDetail createNew(String content) {
        return NoticeDetail.builder()
                           .content(content)
                           .build();
    }

    public void changeNotice(Notice notice) {
        this.notice = notice;
    }
}
