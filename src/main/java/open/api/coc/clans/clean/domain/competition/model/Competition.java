package open.api.coc.clans.clean.domain.competition.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionAlreadyExistsException;
import org.springframework.util.StringUtils;

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

    // 참가 클랜 목록
    @Builder.Default
    private List<CompetitionClan> participantClans = new ArrayList<>();

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

    public void changeCompetition(String name, LocalDate startDate, LocalDate endDate,
                                  String discordUrl, String ruleBookUrl,
                                  Integer roasterSize, String restrictions, String remarks) {

        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        if (Objects.nonNull(startDate)) {
            this.startDate = startDate;
        }
        if (Objects.nonNull(endDate)) {
            this.endDate = endDate;
        }
        if (StringUtils.hasText(discordUrl)) {
            this.discordUrl = discordUrl;
        }
        if (StringUtils.hasText(ruleBookUrl)) {
            this.ruleBookUrl = ruleBookUrl;
        }
        if (Objects.nonNull(roasterSize)) {
            this.roasterSize =roasterSize;
        }
        if (StringUtils.hasText(restrictions)) {
            this.restrictions = restrictions;
        }
        if (StringUtils.hasText(remarks)) {
            this.remarks = remarks;
        }
    }

    public void validateAlreadyParticipated(Clan clan) {
        if (this.isParticipated(clan.getTag())) {
            throw new CompetitionAlreadyExistsException("이미 대회 참가 클랜(%s)".formatted(clan.getName()));
        }
    }

    private boolean isParticipated(String clanTag) {
        return this.participantClans.stream().anyMatch(participantClan -> Objects.equals(participantClan.getClanTag(), clanTag));
    }
}