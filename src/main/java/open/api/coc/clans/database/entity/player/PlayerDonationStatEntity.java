package open.api.coc.clans.database.entity.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(
    name = "tb_player_donation_stat",
    indexes = {
        @Index(name = "PDS_IDX_01", columnList = "player_tag"),
        @Index(name = "PDS_IDX_02", columnList = "season"),
    }
)
@EntityListeners(AuditingEntityListener.class) // @CreatedDate 어노테이션 활성화를 위함
public class PlayerDonationStatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_tag", length = 100, nullable = false)
    private String playerTag;

    @Column(name = "season", nullable = false, updatable = false)
    private String season;

    @Column(name = "donations_delta", nullable = false)
    private int donationsDelta;

    @Column(name = "donations_received_delta", nullable = false)
    private int donationsReceivedDelta;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    @Version
    private Long version;

    protected PlayerDonationStatEntity() {
        // JPA 에서 필요하여 public 유지 설정 조치
        // 동일 패키지에서 인스턴스화 가능하지만 정적 팩토리 메서드 create 를 사용해야 지원 수치 설정 가능함
    }

    private PlayerDonationStatEntity(String playerTag, String season, Integer donationsDelta, Integer donationsReceivedDelta) {
        this.playerTag = playerTag;
        this.season = season;
        this.donationsDelta = donationsDelta;
        this.donationsReceivedDelta = donationsReceivedDelta;
    }

    public static PlayerDonationStatEntity create(String playerTag, String season, Integer lastDonations, Integer lastDonationsReceived, Integer currentDonations, Integer currentDonationsReceived) {
        int donationsDelta = calculateDelta(lastDonations, currentDonations);
        int donationsReceivedDelta = calculateDelta(lastDonationsReceived, currentDonationsReceived);

        return new PlayerDonationStatEntity(playerTag, season, donationsDelta, donationsReceivedDelta);
    }

    private static int calculateDelta(int lastValue, int currentValue) {
        return (currentValue == 0) ? 0 : currentValue - lastValue;
    }

    public void addDonationsDelta(int donationsDelta) {
        this.donationsDelta += donationsDelta;
    }

    public void addDonationsReceivedDelta(int donationsReceivedDelta) {
        this.donationsReceivedDelta += donationsReceivedDelta;
    }
}
