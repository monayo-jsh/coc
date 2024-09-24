package open.api.coc.util;

import open.api.coc.external.coc.clan.domain.common.IconUrl;
import open.api.coc.external.coc.clan.domain.common.Label;

public class TestLabelFactory {

    public static Label create(Integer seq, IconUrl iconUrl) {
        return Label.builder()
                    .id(seq)
                    .name("name-%s".formatted(seq))
                    .iconUrls(iconUrl)
                    .build();
    }

}
