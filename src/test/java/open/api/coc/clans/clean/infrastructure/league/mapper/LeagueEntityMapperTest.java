package open.api.coc.clans.clean.infrastructure.league.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import open.api.coc.clans.clean.infrastructure.common.persistence.mapper.IconUrlEntityMapperImpl;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import open.api.coc.clans.clean.infrastructure.league.persistence.mapper.LeagueEntityMapper;
import open.api.coc.clans.clean.infrastructure.league.persistence.mapper.LeagueEntityMapperImpl;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import open.api.coc.external.coc.clan.domain.common.Label;
import open.api.coc.util.TestIconUrlFactory;
import open.api.coc.util.TestLabelFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LeagueEntityMapper.class)
@Import(value = {
    LeagueEntityMapperImpl.class,
    IconUrlEntityMapperImpl.class
})
class LeagueEntityMapperTest {

    @Autowired
    private LeagueEntityMapper leagueEntityMapper;

    @Test
    @DisplayName("엔티티 변환")
    void toEntity() {
        // given
        IconUrl iconUrl = TestIconUrlFactory.create(new Random().nextLong());
        Label label = TestLabelFactory.create(new Random().nextInt(), iconUrl);

        // when
        LeagueEntity leagueEntity = leagueEntityMapper.toLeagueEntity(label);

        // then
        assertThat(leagueEntity).isNotNull();

        assertThat(leagueEntity.isNew()).isTrue();
        assertThat(leagueEntity.getId()).isEqualTo(label.getId());
        assertThat(leagueEntity.getName()).isEqualTo(label.getName());
        assertThat(leagueEntity.getIconUrl()).isNotNull();
    }
}