package open.api.coc.clans.clean.application.player.model;

public record PlayerListSearchQuery(

    // 계정 유형 (all: 전체 계정, support: 지원 계정)
    String accountType,

    // 조회 모드 (all: 전체 데이터, summary: 플레이어 영웅, 영웅 장비만 제공)
    String viewMode,

    // 이름 검색 조건
    String name

) {

    public boolean isSummaryViewMode() {
        return "summary".equals(viewMode);
    }

}
