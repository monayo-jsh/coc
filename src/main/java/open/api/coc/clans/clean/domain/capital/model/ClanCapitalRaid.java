package open.api.coc.clans.clean.domain.capital.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeasonMember;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanCapitalRaid {

    private Long id; //캐피탈 고유키
    private String clanTag; // 클랜태그

    private String state; // 캐피탈 상태
    private LocalDate startDate; // 캐피탈 시작일
    private LocalDate endDate; // 캐피탈 종료일

    @Builder.Default
    private List<ClanCapitalRaidMember> members = new ArrayList<>(); // 캐피탈 참여자 목록

    public static ClanCapitalRaid createNew(String clanTag, String state, LocalDate startDate, LocalDate endDate, List<ClanCapitalRaidSeasonMember> members) {
        ClanCapitalRaid clanCapitalRaid = ClanCapitalRaid.builder()
                                                         .clanTag(clanTag)
                                                         .state(state)
                                                         .startDate(startDate)
                                                         .endDate(endDate)
                                                         .build();
        clanCapitalRaid.mergeParticipants(members);
        return clanCapitalRaid;
    }

    public void changeRaidInfo(ClanCapitalRaidSeason currentSeason) {
        // 캐피탈 정보
        changeState(currentSeason.getState());

        // 캐피탈 참여 정보
        mergeParticipants(currentSeason.getMembers());
    }

    public void changeState(String state) {
        this.state = state;
    }

    private void mergeParticipants(List<ClanCapitalRaidSeasonMember> members) {
        Map<String, ClanCapitalRaidMember> clanCapitalRaidMemberMap = makeMemberMapByTag(this.members);

        for (ClanCapitalRaidSeasonMember member : members) {
            ClanCapitalRaidMember clanCapitalRaidMember = clanCapitalRaidMemberMap.get(member.getTag());
            if (clanCapitalRaidMember == null) {
                addMember(member);
                continue;
            }

            clanCapitalRaidMember.changeMemberInfo(member.getAttacks(),
                                                   member.getAttackLimit(),
                                                   member.getBonusAttackLimit(),
                                                   member.getCapitalResourcesLooted());
        }
    }

    private void addMember(ClanCapitalRaidSeasonMember member) {
        ClanCapitalRaidMember newMember = ClanCapitalRaidMember.createNew(member.getTag(),
                                                                          member.getName(),
                                                                          member.getAttacks(),
                                                                          member.getAttackLimit(),
                                                                          member.getBonusAttackLimit(),
                                                                          member.getCapitalResourcesLooted(),
                                                                          this.id);

        this.members.add(newMember);
    }

    public void mappingParticipantIds(List<ClanCapitalRaidMember> members) {
        Map<String, ClanCapitalRaidMember> memberMap = makeMemberMapByTag(members);

        // 신규 생성된 참여자의 고유키 매핑
        for( ClanCapitalRaidMember member : this.members) {
            ClanCapitalRaidMember clanCapitalRaidMember = memberMap.get(member.getTag());
            member.assignIdIfAbsent(clanCapitalRaidMember.getId());
            member.assignRaidIdIfAbsent(this.id);
        }
    }

    private Map<String, ClanCapitalRaidMember> makeMemberMapByTag(List<ClanCapitalRaidMember> members) {
        return members.stream()
                      .collect(Collectors.toMap(ClanCapitalRaidMember::getTag, member -> member));
    }

    public List<ClanCapitalRaidMember> getViolationMembers() {
        if (this.members.isEmpty()) return this.members;

        // 위반 규칙
        // 1. 캐피탈 점수 20000점 기준으로 상위/하위 티어 구분
        final int highTierMinimumScore = 20000;

        return this.members.stream()
                           .filter(member -> member.isLessThenOrEqualsResourceLooted(highTierMinimumScore))
                           .toList();
    }

    public boolean isNotEnded() {
        return Objects.equals(this.state, "ended");
    }

    public void assignIdIfAbsent(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }
}
