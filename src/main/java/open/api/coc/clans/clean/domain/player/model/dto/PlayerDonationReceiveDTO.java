package open.api.coc.clans.clean.domain.player.model.dto;

public record PlayerDonationReceiveDTO(

    String tag, // 플레이어 태그
    String name, // 플레이어 이름
    Integer townHallLevel, // 타운홀 레벨
    Integer count // 지원수

) {
}
