package open.api.coc.clans.clean.domain.capital.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public static ClanCapitalRaid createNew(String clanTag, String state, LocalDateTime startTime, LocalDateTime endTime) {
        return ClanCapitalRaid.builder()
                              .clanTag(clanTag)
                              .state(state)
                              .startDate(startTime.toLocalDate())
                              .endDate(endTime.toLocalDate())
                              .build();
    }

    public boolean isDifferentState(String state) {
        return !Objects.equals(this.state, state);
    }
    public void changeState(String state) {
        this.state = state;
    }

    public void updateParticipants(List<ClanCapitalRaidSeasonMember> members) {
        Map<String, ClanCapitalRaidMember> clanCapitalRaidMemberMap = makeMemberMapByPlayerTag(this.members);

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
        Map<String, ClanCapitalRaidMember> clanCapitalRaidMemberMap = makeMemberMapByPlayerTag(members);

        // 신규 생성된 참여자의 고유키 매핑
        for( ClanCapitalRaidMember member : this.members) {
            ClanCapitalRaidMember clanCapitalRaidMember = clanCapitalRaidMemberMap.get(member.getTag());
            member.changeId(clanCapitalRaidMember.getId());
        }
    }

    private Map<String, ClanCapitalRaidMember> makeMemberMapByPlayerTag(List<ClanCapitalRaidMember> members) {
        return members.stream()
                      .collect(Collectors.toMap(ClanCapitalRaidMember::getTag, member -> member));
    }
}
