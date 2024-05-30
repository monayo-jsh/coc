package open.api.coc.clans.database.entity.player;

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
import open.api.coc.clans.database.entity.league.LeagueEntity;
import open.api.coc.clans.database.entity.player.common.WarPreferenceType;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_player")
public class PlayerEntity extends BaseEntity implements Persistable<String> {

    @Id
    @Column(name = "player_tag", length = 100)
    private String playerTag;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "support_yn", nullable = false)
    private YnType supportYn = YnType.N;

    @Column(name = "exp_level", nullable = false)
    private Integer expLevel;

    @Column(name = "town_halll_level", nullable = false)
    private Integer townHallLevel;

    @Column(name = "trophies", nullable = false)
    private Integer trophies;

    @Column(name = "best_trophies", nullable = false)
    private Integer bestTrophies;

    @Column(name = "warStars", nullable = false)
    private Integer warStars;

    @Column(name = "donations", nullable = false)
    private Integer donations;

    @Column(name = "donations_received", nullable = false)
    private Integer donationsReceived;

    @Column(name = "attackWins", nullable = false)
    private Integer attackWins;

    @Column(name = "defenseWins", nullable = false)
    private Integer defenseWins;

    @Column(name = "role")
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "war_preference")
    private WarPreferenceType warPreference;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "clan_tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ClanEntity clan;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "league_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private LeagueEntity league;

    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerHeroEntity> heroes = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerHeroEquipmentEntity> heroEquipments = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerTroopsEntity> troops = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "player", cascade = CascadeType.ALL)
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
        this.heroes = heroes;

        for (PlayerHeroEntity hero : this.heroes) {
            hero.changePlayer(this);
        }
    }

    public void addHeroEquipment(PlayerHeroEquipmentEntity heroEquipment) {
        heroEquipment.changePlayer(this);
        heroEquipments.add(heroEquipment);
    }

    public void changeHeroEquipments(List<PlayerHeroEquipmentEntity> heroEquipments) {
        this.heroEquipments = heroEquipments;

        for (PlayerHeroEquipmentEntity heroEquipment : this.heroEquipments) {
            heroEquipment.changePlayer(this);
        }
    }

    public void addTroop(PlayerTroopsEntity troop) {
        troop.changePlayer(this);
        this.troops.add(troop);
    }

    public void changeTroops(List<PlayerTroopsEntity> troops) {
        this.troops = troops;

        for (PlayerTroopsEntity troop : this.troops) {
            troop.changePlayer(this);
        }
    }

    public void addSpell(PlayerSpellEntity spell) {
        spell.changePlayer(this);
        this.spells.add(spell);
    }
    public void changeSpells(List<PlayerSpellEntity> spells) {
        this.spells = spells;

        for (PlayerSpellEntity spell : this.spells) {
            spell.changePlayer(this);
        }
    }
}
