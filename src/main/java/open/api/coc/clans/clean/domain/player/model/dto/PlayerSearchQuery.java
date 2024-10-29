package open.api.coc.clans.clean.domain.player.model.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(access = AccessLevel.PRIVATE)
public record PlayerSearchQuery(

    // 계정 유형 (all: 전체 계정, support: 지원 계정)
    String accountType,

    // 이름 검색 조건
    String name,

    // 태그 검색 조건
    List<String> tags

) {

    public static PlayerSearchQuery create(String accountType, String name) {
        return PlayerSearchQuery.builder()
                                .accountType(accountType)
                                .name(name)
                                .build();
    }

    public static PlayerSearchQuery createWithTags(List<String> playerTags) {
        return PlayerSearchQuery.builder()
                                .tags(playerTags)
                                .build();
    }

    public static PlayerSearchQuery empty() {
        return PlayerSearchQuery.builder().build();
    }

    public boolean isFilterSupport() {
        return "support".equals(accountType);
    }

    public boolean isNameSearch() {
        return StringUtils.hasText(name);
    }

    public boolean isTagSearch() {
        return !CollectionUtils.isEmpty(tags);
    }
}
