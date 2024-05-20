package open.api.coc.clans.domain.clans.converter;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.IconUrlResponse;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import open.api.coc.external.coc.clan.domain.common.Label;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LabelResponseConverter implements Converter<Label, LabelResponse> {

    private final IconUrlResponseConverter iconUrlResConverter;

    @Override
    public LabelResponse convert(Label source) {
        return LabelResponse.builder()
                            .id(source.getId())
                            .name(source.getName())
                            .iconUrls(makeIconUrls(source.getIconUrls()))
                            .build();
    }

    private IconUrlResponse makeIconUrls(IconUrl iconUrl) {
        // 공통 사용 객체로 응답 규격마다 없는 경우가 있음
        if (Objects.isNull(iconUrl)) return null;
        return iconUrlResConverter.convert(iconUrl);
    }

}