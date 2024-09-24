package open.api.coc.util;

import open.api.coc.external.coc.clan.domain.common.IconUrl;

public class TestIconUrlFactory {

    public static IconUrl create(Long seq) {
        return IconUrl.builder()
                      .tiny("tiny-%s".formatted(seq))
                      .small("small-%s".formatted(seq))
                      .medium("medium-%s".formatted(seq))
                      .large("large-%s".formatted(seq))
                      .build();
    }

}
