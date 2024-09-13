package open.api.coc.clans.clean.domain.competition.model;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Competition {

    // 대회 고유키
    private Long id;
    // 대회 이름
    private String name;
    // 대회 시작
    private LocalDate startDate;
    // 대회 종료
    private LocalDate endDate;
    // 대회 디스코드 URL
    private String discordUrl;
    // 대회 룰북 URL
    private String ruleBookUrl;
    // 최대 등록 멤버수
    private Integer roasterSize;
    // 제한사항
    private String restrictions;
    // 메모
    private String remarks;

    public static Competition createNew(String name, LocalDate startDate, LocalDate endDate,
                                        String discordUrl, String ruleBookUrl,
                                        Integer roasterSize, String restrictions, String remarks) {
        return Competition.builder()
                          .name(name)
                          .startDate(startDate)
                          .endDate(endDate)
                          .discordUrl(discordUrl)
                          .ruleBookUrl(ruleBookUrl)
                          .roasterSize(roasterSize)
                          .restrictions(restrictions)
                          .remarks(remarks)
                          .build();
    }
}
