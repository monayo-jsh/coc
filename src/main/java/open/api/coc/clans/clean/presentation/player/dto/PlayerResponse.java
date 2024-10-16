package open.api.coc.clans.clean.presentation.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.clean.presentation.common.dto.ClanSimpleResponse;
import open.api.coc.clans.clean.presentation.league.dto.LeagueResponse;

@Getter
@Builder
@AllArgsConstructor
public class PlayerResponse {

    @Schema(description = "태그")
    private String tag;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "지원계정 여부 - Y: 지원계정, N: 아님")
    private String supportYn;

    @Schema(description = "레벨")
    private Integer expLevel;
    @Schema(description = "타운홀 레벨")
    private Integer townHallLevel;

    @Schema(description = "현재 트로피")
    private Integer trophies;
    @Schema(description = "최고 트로피")
    private Integer bestTrophies;

    @Schema(description = "공격 성공수")
    private Integer attackWins;
    @Schema(description = "방어 성공수")
    private Integer defenseWins;

    @Schema(description = "전쟁 획득 별")
    private Integer warStars;

    @Schema(description = "현재 리그 정보")
    private LeagueResponse league;

    @Schema(description = "가입 클랜 직위 - leader: 대표, coLeader: 공동대표, admin: 장로, member: 일반")
    private String role;
    @Schema(description = "가입 클랜 전쟁 선호도 - in: 참가, out: 불참")
    private String warPreference;
    @Schema(description = "가입 클랜 정보")
    private ClanSimpleResponse clan;

    @Schema(description = "현재 지원수")
    private Integer donations;
    @Schema(description = "현재 지원 받은수")
    private Integer donationsReceived;

    @Schema(description = "영웅 목록")
    private List<PlayerHeroResponse> heroes;

    @Schema(description = "영웅장비 목록")
    private List<PlayerHeroEquipmentResponse> heroEquipments;

    @Schema(description = "마법 목록")
    private List<PlayerTroopResponse> spells;

    @Schema(description = "펫 목록")
    private List<PlayerTroopResponse> pets;

    @Schema(description = "시즈머신 목록")
    private List<PlayerTroopResponse> siegeMachines;

    @Schema(description = "보유 영웅 레벨 합")
    private Integer heroTotalLevel;

    @Schema(description = "보유 영웅 최대 레벨 합")
    private Integer heroTotalMaxLevel;

}
