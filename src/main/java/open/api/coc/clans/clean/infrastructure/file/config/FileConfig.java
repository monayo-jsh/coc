package open.api.coc.clans.clean.infrastructure.file.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "file")
@RequiredArgsConstructor
public class FileConfig {

    private final String uploadPath;

}
