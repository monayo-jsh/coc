package open.api.coc.external.coc.config;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroConfig {

    BARBARIAN_KING(1, "Barbarian King"),
    ARCHER_QUEEN(2, "Archer Queen"),
    GRAND_WARDEN(3, "Grand Warden"),
    ROYAL_CHAMPION(4, "Royal Champion");

    private final int code;
    private final String name;

    HeroConfig(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HeroConfig findByName(String name) {
        return Arrays.stream(values())
                     .filter(hero -> Objects.equals(hero.getName(), name))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("영웅 코드를 찾지 못함"));
    }
}
