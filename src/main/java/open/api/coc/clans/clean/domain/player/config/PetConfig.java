package open.api.coc.clans.clean.domain.player.config;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.clean.domain.player.model.PlayerTroopType;

@Getter
public enum PetConfig {

    LASSI(PlayerTroopType.PET, 1, "L.A.S.S.I", "라씨"),
    MIGHTY_YAK(PlayerTroopType.PET, 2, "Mighty Yak", "마이트 야크"),
    ELECTRO_OWL(PlayerTroopType.PET, 3, "Electro Owl", "일렉트로 아울"),
    UNICORN(PlayerTroopType.PET, 4, "Unicorn",  "유니콘"),
    PHOENIX(PlayerTroopType.PET, 5, "Phoenix",  "피닉스"),
    POISON_LIZARD(PlayerTroopType.PET, 6, "Poison Lizard",  "독마뱀"),
    DIGGY(PlayerTroopType.PET, 7, "Diggy",  "디기"),
    FROSTY(PlayerTroopType.PET, 8, "Frosty",  "프로스티"),
    SPIRIT_FOX(PlayerTroopType.PET, 9, "Spirit Fox", "스피릿 폭스"),
    ANGRY_JELLY(PlayerTroopType.PET, 10, "Angry Jelly", "앵그리 젤리"),

    UNKNOWN(PlayerTroopType.UNKNOWN, 99,"unknown", "알 수 없음");

    private final PlayerTroopType type;
    private final Integer order;
    private final String name;
    private final String koreanName;

    PetConfig(PlayerTroopType type, Integer order, String name, String koreanName) {
        this.type = type;
        this.order = order;
        this.name = name;
        this.koreanName = koreanName;
    }

    public static PetConfig findByName(String troopName) {
        return Arrays.stream(values())
                     .filter(troop -> Objects.equals(troop.getName(), troopName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

    public boolean isPet() {
        return this.type.equals(PlayerTroopType.PET);
    }
}
