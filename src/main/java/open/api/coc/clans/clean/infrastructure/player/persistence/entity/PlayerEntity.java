package open.api.coc.clans.clean.infrastructure.player.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.common.BaseEntity;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "tb_player",
    indexes = @Index(name = "idx_player_name", columnList = "name")
)
@Comment("플레이어 테이블")
public class PlayerEntity extends BaseEntity implements Persistable<String> {

    @Comment("플레이어 태그")
    @Id
    @Column(name = "player_tag", nullable = false, length = 100)
    private String playerTag;

    @Comment("플레이어 이름")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Comment("지원계정 여부")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "support_yn", nullable = false, length = 10)
    private YnType supportYn = YnType.N;

    @Comment("플레이어 레벨")
    @Column(name = "exp_level", nullable = false)
    private Integer expLevel;

    @Comment("플레이어 타운홀 레벨")
    @Column(name = "town_halll_level", nullable = false)
    private Integer townHallLevel;

    @Comment("플레이어 현재 트로피")
    @Column(name = "trophies", nullable = false)
    private Integer trophies;

    @Comment("플레이어 최대 트로피")
    @Column(name = "best_trophies", nullable = false)
    private Integer bestTrophies;

    @Comment("플레이어 공격 성공수")
    @Column(name = "attackWins", nullable = false)
    private Integer attackWins;

    @Comment("플레이어 방어 성공수")
    @Column(name = "defenseWins", nullable = false)
    private Integer defenseWins;

    @Comment("플레이어 전쟁 획득 별")
    @Column(name = "warStars", nullable = false)
    private Integer warStars;

    @Comment("플레이어 지원수")
    @Column(name = "donations", nullable = false)
    private Integer donations;

    @Comment("플레이어 지원받은수")
    @Column(name = "donations_received", nullable = false)
    private Integer donationsReceived;

    @Comment("플레이어 가입 클랜 직위 - leader: 대표, coLeader: 공동대표, admin: 장로, member: 일반")
    @Column(name = "role")
    private String role;

    @Comment("플레이어 전쟁선호도")
    @Enumerated(EnumType.STRING)
    @Column(name = "war_preference")
    private WarPreferenceType warPreference;

    @Comment("플레이어 현재 가입 클랜 태그")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "clan_tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ClanEntity clan;

    @Comment("플레이어 현재 리그")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "league_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private LeagueEntity league;

    @Comment("플레이어 영웅 목록")
    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerHeroEntity> heroes = new ArrayList<>();

    @Comment("플레이어 영웅장비 목록")
    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerHeroEquipmentEntity> heroEquipments = new ArrayList<>();

    @Comment("플레이어 유닛 목록")
    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerTroopsEntity> troops = new ArrayList<>();

    @Comment("플레이어 마법 목록")
    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerSpellEntity> spells = new ArrayList<>();

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.playerTag;
    }

    public void changeLeague(LeagueEntity league) {
        league.add(this);
        this.league = league;
    }

    public void changeClan(ClanEntity clan) {
        clan.add(this);
        this.clan = clan;
    }

    public void addHero(PlayerHeroEntity hero) {
        hero.changePlayer(this);
        this.heroes.add(hero);
    }

    public void changeHeroes(List<PlayerHeroEntity> heroes) {
        this.heroes.clear();
        heroes.forEach(this::addHero);
    }

    public void addHeroEquipment(PlayerHeroEquipmentEntity heroEquipment) {
        heroEquipment.changePlayer(this);
        heroEquipments.add(heroEquipment);
    }

    public void changeHeroEquipments(List<PlayerHeroEquipmentEntity> heroEquipments) {
        this.heroEquipments.clear();
        heroEquipments.forEach(this::addHeroEquipment);
    }

    public void addTroop(PlayerTroopsEntity troop) {
        troop.changePlayer(this);
        this.troops.add(troop);
    }

    public void changeTroops(List<PlayerTroopsEntity> troops) {
        this.troops.clear();
        troops.forEach(this::addTroop);
    }

    public void addSpell(PlayerSpellEntity spell) {
        spell.changePlayer(this);
        this.spells.add(spell);
    }
    public void changeSpells(List<PlayerSpellEntity> spells) {
        this.spells.clear();
        spells.forEach(this::addSpell);
    }

    public void changeSupportYn(YnType supportYn) {
        this.supportYn = supportYn;
    }
}
