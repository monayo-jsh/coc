package open.api.coc.clans.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("아카데미 편의 기능 APIs")
                              .version("1.0")
                              .description("편의/운영 기능 구현을 위한 API 연동 규격서")
                              .contact(new Contact().name("monayo")
                                                    .email("monayo.jsh@gmail.com")
                                                    .url("https://github.com/monayo-jsh"));

        return new OpenAPI().info(info);
    }

}