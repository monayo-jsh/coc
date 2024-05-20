package open.api.external.coc.config;

import static org.assertj.core.api.Assertions.assertThat;

import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(ClashOfClanConfig.class)
class ClashOfClanConfigTest {

    @Autowired
    private ClashOfClanConfig clashOfClanConfig;

    @Test
    @DisplayName("configuration check not null")
    void testNotNull() {
        assertThat(clashOfClanConfig.getApiKey()).isNotBlank();

        assertThat(clashOfClanConfig.getDomain()).isNotBlank();
        assertThat(clashOfClanConfig.getDomain()).isEqualTo("https://api.clashofclans.com");

        assertThat(clashOfClanConfig.getClansClanTagUri()).isNotBlank();
        assertThat(clashOfClanConfig.getClansClanTagCapitalRaidSeasonsUri()).isNotBlank();
        assertThat(clashOfClanConfig.getClansClanTagCurrentWarUri()).isNotBlank();

        assertThat(clashOfClanConfig.getPlayerUri()).isNotBlank();

        assertThat(clashOfClanConfig.getLeaguesUri()).isNotBlank();
    }

}