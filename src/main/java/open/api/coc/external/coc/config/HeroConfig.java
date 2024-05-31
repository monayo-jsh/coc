package open.api.coc.external.coc.config;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroConfig {

    BARBARIAN_KING(1, "Barbarian King", "바바리안 킹", "킹"),
    ARCHER_QUEEN(2, "Archer Queen", "아처 퀸", "퀸"),
    GRAND_WARDEN(3, "Grand Warden", "그랜드 워든", "워든"),
    ROYAL_CHAMPION(4, "Royal Champion", "로얄 챔피언", "로챔"),

    UNKNOWN(Integer.MAX_VALUE, "unknown", "알 수 없음", "알 수 없음");

    private final int code;
    private final String name;
    private final String koreanName;
    private final String koreanShortName;

    HeroConfig(int code, String name, String koreanName, String koreanShortName) {
        this.code = code;
        this.name = name;
        this.koreanName = koreanName;
        this.koreanShortName = koreanShortName;
    }

    public static HeroConfig findByName(String name) {
        return Arrays.stream(values())
                     .filter(hero -> Objects.equals(hero.getName(), name))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("영웅 코드를 찾지 못함"));
    }
}
