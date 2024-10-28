package open.api.coc.clans.clean.domain.player.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder(access = AccessLevel.PRIVATE)
public record PlayerSearchQuery(

    // 계정 유형 (all: 전체 계정, support: 지원 계정)
    String accountType,

    // 이름 검색 조건
    String name

) {

    public static PlayerSearchQuery create(String accountType, String name) {
        return PlayerSearchQuery.builder()
                                .accountType(accountType)
                                .name(name)
                                .build();
    }

    public boolean isFilterSupport() {
        return "support".equals(accountType);
    }

    public boolean isNameSearch() {
        return StringUtils.hasText(name);
    }
}
