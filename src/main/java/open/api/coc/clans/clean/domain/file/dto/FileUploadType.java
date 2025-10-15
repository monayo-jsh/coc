package open.api.coc.clans.clean.domain.file.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public enum FileUploadType {
    RULE_BOOK("rulebook"),
    CLAN_GAME("clan-game");

    private final String path;

    FileUploadType(String path) {
        this.path = path;
    }

    public String getFileName() {
        return switch (this) {
            case RULE_BOOK -> "latest.xlsx";
            case CLAN_GAME -> "%s.png".formatted(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
        };
    }
}
