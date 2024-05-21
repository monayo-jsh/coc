package open.api.coc.external.coc.clan.domain.common;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.common.exception.NotFoundException;

@Getter
public enum Pet {

    LASSI("L.A.S.S.I", "라씨"),
    MIGHTY_YAK("Mighty Yak", "마이트 야크"),
    ELECTRO_OWL("Electro Owl", "일렉트로 아울"),
    UNICORN("Unicorn", "유니콘"),
    PHOENIX("Phoenix", "피닉스"),
    POISON_LIZARD("Poison Lizard", "독마뱀"),
    DIGGY("Diggy", "디기"),
    FROSTY("Frosty", "프로스티"),
    SPIRIT_FOX("Spirit Fox", "스피릿 폭스"),
    ANGRY_JELLY("Angry Jelly", "앵그리 젤리");

    private final String name;
    private final String koreanName;

    Pet(String name, String koreanName) {
        this.name = name;
        this.koreanName = koreanName;
    }

    public static Pet findByName(String name) {
        return Arrays.stream(values())
                     .filter(hero -> Objects.equals(hero.getName(), name))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("펫(%s) 정의 코드를 찾지 못함".formatted(name)));
    }

    public static boolean isPets(String name) {
        try {
            Pet pet = findByName(name);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
