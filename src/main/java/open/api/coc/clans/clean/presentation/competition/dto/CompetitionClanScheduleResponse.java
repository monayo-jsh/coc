package open.api.coc.clans.clean.presentation.competition.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class CompetitionClanScheduleResponse {

    private Long id;                // 대회 참여 클랜 일정 고유키
    private Long compClanId;        // 대회 참여 클랜 유니크키
    private String description;     // 라운드 설명
    private LocalDate startDate;    // 라운드 시작일
    private LocalDate endDate;      // 라운드 종료일
    private LocalDateTime fixedAt;  // 라운드 확정일시 (시작~종료일 기간 중 네고된 일시)

}
