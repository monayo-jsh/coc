package open.api.coc.clans.clean.domain.player.model.dto;

import open.api.coc.clans.database.entity.common.YnType;

public record PlayerDonationDTO(

    String tag, // 플레이어 태그
    String name, // 플레이어 이름
    Integer townHallLevel, // 타운홀 레벨
    YnType supportYn, // 지원계정 여부
    Integer donationCount // 지원수

) {
}
