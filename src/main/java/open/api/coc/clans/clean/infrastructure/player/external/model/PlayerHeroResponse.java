package open.api.coc.clans.clean.infrastructure.player.external.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerHeroResponse {

    private String name; // 이름
    private String village; // 제공 마을

    private Integer level; // 현재 레벨
    private Integer maxLevel; // 최대 레벨

    @Builder.Default
    private List<PlayerHeroEquipmentResponse> equipment = new ArrayList<>(); // 장착중인 장비 목록

    public boolean isVillageHome() {
        return Objects.equals("home", village);
    }

    public Stream<PlayerHeroEquipmentResponse> getEquipmentsStream() {
        return this.equipment.stream();
    }
}
