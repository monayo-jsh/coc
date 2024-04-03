package open.api.external.coc.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertNotNull(clashOfClanConfig.getApiKey());
    }

}