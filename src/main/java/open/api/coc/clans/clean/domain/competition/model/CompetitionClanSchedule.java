package open.api.coc.clans.clean.domain.competition.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CompetitionClanSchedule {

    private Long id;                // 대회 참여 클랜 일정 고유키
    private Long compClanId;        // 대회 참여 클랜 유니크키
    private String description;     // 라운드 설명
    private LocalDate startDate;    // 라운드 시작일
    private LocalDate endDate;      // 라운드 종료일
    private LocalDateTime fixedAt;  // 라운드 확정일시 (시작~종료일 기간 중 네고된 일시)

    public static CompetitionClanSchedule createNew(Long compClanId, String description,
                                                    LocalDate startDate, LocalDate endDate) {

        return CompetitionClanSchedule.builder()
                                      .compClanId(compClanId)
                                      .description(description)
                                      .startDate(startDate)
                                      .endDate(endDate)
                                      .build();

    }

}
