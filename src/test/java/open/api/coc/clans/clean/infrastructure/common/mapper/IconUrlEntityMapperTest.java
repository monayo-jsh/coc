package open.api.coc.clans.clean.infrastructure.common.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import open.api.coc.clans.clean.infrastructure.common.persistence.entity.IconUrlEntity;
import open.api.coc.clans.clean.infrastructure.common.persistence.mapper.IconUrlEntityMapper;
import open.api.coc.clans.clean.infrastructure.common.persistence.mapper.IconUrlEntityMapperImpl;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import open.api.coc.util.TestIconUrlFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = IconUrlEntityMapper.class)
@Import(IconUrlEntityMapperImpl.class)
class IconUrlEntityMapperTest {

    @Autowired
    private IconUrlEntityMapper iconUrlEntityMapper;

    @Test
    @DisplayName("엔티티 변환")
    void toEntity() {
        // given
        IconUrl testIconUrl = TestIconUrlFactory.create(new Random().nextLong());

        // when
        IconUrlEntity iconUrlEntity = iconUrlEntityMapper.toEntity(testIconUrl);

        // then
        assertThat(iconUrlEntity).isNotNull();
        assertThat(iconUrlEntity.getTiny()).isEqualTo(testIconUrl.getTiny());
        assertThat(iconUrlEntity.getSmall()).isEqualTo(testIconUrl.getSmall());
        assertThat(iconUrlEntity.getMedium()).isEqualTo(testIconUrl.getMedium());
        assertThat(iconUrlEntity.getLarge()).isEqualTo(testIconUrl.getLarge());
    }
}