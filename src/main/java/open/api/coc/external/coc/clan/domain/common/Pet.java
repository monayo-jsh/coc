package open.api.coc.external.coc.clan.domain.common;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.common.exception.CustomRuntimeException;

@Getter
public enum Pet {

    LASSI("L.A.S.S.I"),
    MIGHTY_YAK("Mighty Yak"),
    ELECTRO_OWL("Electro Owl"),
    UNICORN("Unicorn"),
    PHOENIX("Phoenix"),
    POISON_LIZARD("Poison Lizard"),
    DIGGY("Diggy"),
    FROSTY("Frosty"),
    SPIRIT_FOX("Spirit Fox"),
    ANGRY_JELLY("Angry Jelly");

    private final String name;

    Pet(String name) {
        this.name = name;
    }

    public static Pet findByName(String name) {
        return Arrays.stream(values())
                     .filter(hero -> Objects.equals(hero.getName(), name))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("펫 정의 코드를 찾지 못함"));
    }

    public static boolean isPets(String name) {
        try {
            Pet pet = findByName(name);
            return true;
        } catch (CustomRuntimeException e) {
            return false;
        }
    }
}
