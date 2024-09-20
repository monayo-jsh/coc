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
import open.api.coc.clans.clean.domain.competition.exception.CompetitionClanRoasterAlreadyExistsException;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionClanScheduleDuplicateException;
import open.api.coc.clans.clean.domain.competition.service.CompetitionClanScheduleService;
import open.api.coc.clans.clean.domain.competition.service.CompetitionClanRoasterService;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CompetitionClan {

    // 대회 참가 고유키
    private Long id;
    // 대회 고유키
    private Long compId;
    // 대회 참여 클랜 태그
    private String tag;
    // 대회 참여 클랜 이름
    private String name;
    // 대회 참여 상태
    private String status;

    // 대회 참여 클랜 등록 멤버
    @Builder.Default
    private List<CompetitionClanRoaster> roasters = new ArrayList<>();

    @Builder.Default
    private List<CompetitionClanSchedule> schedules = new ArrayList<>();

    public static CompetitionClan createNew(Long compId, String clanTag) {
        return CompetitionClan.builder()
                              .compId(compId)
                              .tag(clanTag)
                              .build();
    }

    public boolean isEqualsClanTag(String clanTag) {
        return Objects.equals(tag, clanTag);
    }

    public void loadRoaster(CompetitionClanRoasterService competitionParticipateClanService) {
        List<CompetitionClanRoaster> roasters = competitionParticipateClanService.findAllByCompClanId(this.id);
        this.roasters.clear();
        this.roasters.addAll(roasters);
    }

    public void loadSchedules(CompetitionClanScheduleService competitionClanScheduleService) {
        List<CompetitionClanSchedule> schedules = competitionClanScheduleService.findAllByCompClanId(this.id);
        this.schedules.clear();
        this.schedules.addAll(schedules);
    }

    public void addSchedule(CompetitionClanSchedule clanSchedule) {
        // 기존 일정과 중복 검증
        validateDuplicatedSchedule(clanSchedule.getStartDate());

        this.schedules.add(clanSchedule);
    }

    private void validateDuplicatedSchedule(LocalDate startDate) {
        if (isExistsStartDate(startDate)) {
            throw new CompetitionClanScheduleDuplicateException(startDate);
        }
    }

    private boolean isExistsStartDate(LocalDate startDate) {
        return this.schedules.stream()
                             .anyMatch(schedule -> startDate.isEqual(schedule.getStartDate()));
    }

    public void addRoaster(CompetitionClanRoaster clanRoaster) {
        // 기존에 등록된 멤버 검증
        validateAlreadyRegistered(clanRoaster);

        this.roasters.add(clanRoaster);
    }

    private void validateAlreadyRegistered(CompetitionClanRoaster roaster) {
        if (isRegistered(roaster)) {
            throw new CompetitionClanRoasterAlreadyExistsException(roaster.getPlayerTag());
        }
    }

    private boolean isRegistered(CompetitionClanRoaster checkRoaster) {
        return this.roasters.stream().anyMatch(roaster -> roaster.isEquals(checkRoaster));
    }
}
