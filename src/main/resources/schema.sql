CREATE TABLE TB_CLAN (
    TAG VARCHAR(100) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    REG_DATE TIMESTAMP(6) NOT NULL,
    ORDERS INTEGER NOT NULL,
    VISIBLE_YN VARCHAR(255) NOT NULL CHECK (VISIBLE_YN IN ('Y','N')),
    PRIMARY KEY (TAG)
);

CREATE TABLE TB_CLAN_ASSIGNED_PLAYER (
    CLAN_TAG VARCHAR(100),
    SEASON_DATE VARCHAR(6) NOT NULL,
    PLAYER_TAG VARCHAR(100) NOT NULL,
    PRIMARY KEY (SEASON_DATE, PLAYER_TAG)
);

CREATE TABLE TB_CLAN_BADGE (
    TAG VARCHAR(100) NOT NULL,
    LARGE VARCHAR(500),
    MEDIUM VARCHAR(500),
    SMALL VARCHAR(500),
    TINY VARCHAR(500),
    PRIMARY KEY (TAG)
);

CREATE TABLE TB_CLAN_CONTENT (
    TAG VARCHAR(100) NOT NULL,
    CLAN_WAR_YN VARCHAR(1) NOT NULL,
    WAR_LEAGUE_YN VARCHAR(1) NOT NULL,
    CLAN_CAPITAL_YN VARCHAR(1) NOT NULL,
    CLAN_WAR_PARALLEL_YN VARCHAR(1) NOT NULL,
    PRIMARY KEY (TAG)
);
CREATE INDEX IDX_01 ON TB_CLAN_ASSIGNED_PLAYER (CLAN_TAG, SEASON_DATE);

CREATE TABLE TB_LEAGUE (
    LEAGUE_ID INTEGER NOT NULL,
    LEAGUE_NAME VARCHAR(255) NOT NULL,
    TINY VARCHAR(500),
    SMALL VARCHAR(500),
    MEDIUM VARCHAR(500),
    LARGE VARCHAR(500),
    PRIMARY KEY (LEAGUE_ID)
);

CREATE TABLE TB_PLAYER (
    PLAYER_TAG VARCHAR(100) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    EXP_LEVEL INTEGER NOT NULL,
    TOWN_HALLL_LEVEL INTEGER NOT NULL,
    TROPHIES INTEGER NOT NULL,
    BEST_TROPHIES INTEGER NOT NULL,
    WAR_STARS INTEGER NOT NULL,
    ATTACK_WINS INTEGER NOT NULL,
    DEFENSE_WINS INTEGER NOT NULL,
    LEAGUE_ID INTEGER,
    CLAN_TAG VARCHAR(100),
    WAR_PREFERENCE VARCHAR(255) CHECK (WAR_PREFERENCE IN ('in','out')),
    CREATED_DATE TIMESTAMP(6) NOT NULL,
    LAST_MODIFIED_DATE TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (PLAYER_TAG)
);

CREATE TABLE TB_PLAYER_HERO (
    PLAYER_TAG VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    MAX_LEVEL INTEGER NOT NULL,
    LEVEL INTEGER NOT NULL,
    PRIMARY KEY (PLAYER_TAG, NAME)
);

CREATE TABLE TB_PLAYER_HERO_EQUIPMENT (
    PLAYER_TAG VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    MAX_LEVEL INTEGER NOT NULL,
    LEVEL INTEGER NOT NULL,
    TARGET_HERO_NAME VARCHAR(255) NOT NULL,
    WEAR_YN VARCHAR(255) NOT NULL CHECK (WEAR_YN IN ('Y','N')),
    PRIMARY KEY (PLAYER_TAG, NAME)
);

CREATE TABLE TB_PLAYER_SPELL (
    PLAYER_TAG VARCHAR(100) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    MAX_LEVEL INTEGER NOT NULL,
    LEVEL INTEGER NOT NULL,
    PRIMARY KEY (PLAYER_TAG, NAME)
);

CREATE TABLE TB_PLAYER_TROOPS (
    PLAYER_TAG VARCHAR(100) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    MAX_LEVEL INTEGER NOT NULL,
    LEVEL INTEGER NOT NULL,
    TYPE VARCHAR(255) NOT NULL CHECK (TYPE IN ('ELIXIR','DARK_ELIXIR','SUPER_TROOP','SIEGE_MACHINE','PET','UNKNOWN')),
    PRIMARY KEY (PLAYER_TAG, NAME)
);

-- insert into tb_clan values('#2QJUL08V9', '아카데미', 1, now());
-- insert into tb_clan values('#2QG0G20RR', '아카데미 2.0', 2, now());
-- insert into tb_clan values('#2LPP8PUCG', '아카데미 3.0', 3, now());
-- insert into tb_clan values('#2L209LC8P', '아카데미 4.0', 4, now());
-- insert into tb_clan values('#2QV0R0L8R', '아카데미 5.0', 5, now());
-- insert into tb_clan values('#2GJGRU920', 'Academe', 6, now());
-- insert into tb_clan values('#2GPGU92Q2', 'TEAM Academe', 7, now());
-- insert into tb_clan values('#2GY9YL0J9', 'TEAM Bcademe', 8, now());
-- insert into tb_clan values('#2QJLPVPQU', 'TEAM Ccademe', 9, now());
-- insert into tb_clan values('#229V992R8', '클랜전리그', 20, now());
-- insert into tb_clan values('#2G9GU9PLU', '육룡이 나르샤', 21, now());
-- insert into tb_clan values('#2PLJJLY89', '가래떡에 꿀', 22, now());
-- insert into tb_clan values('#2PUPJ09VP', '가래떡에 설탕', 23, now());
-- insert into tb_clan values('#2LJ0U02YJ', '아카데미 쉼터', 99, now());
--
-- insert into tb_clan_content(tag) values('#2QJUL08V9');
-- insert into tb_clan_content(tag) values('#2QG0G20RR');
-- insert into tb_clan_content(tag) values('#2LPP8PUCG');
-- insert into tb_clan_content(tag) values('#2L209LC8P');
-- insert into tb_clan_content(tag) values('#2QV0R0L8R');
-- insert into tb_clan_content(tag) values('#2GJGRU920');
-- insert into tb_clan_content(tag) values('#2GPGU92Q2');
-- insert into tb_clan_content(tag) values('#2GY9YL0J9');
-- insert into tb_clan_content(tag) values('#2QJLPVPQU');
-- insert into tb_clan_content(tag) values('#229V992R8');
-- insert into tb_clan_content(tag) values('#2G9GU9PLU');
-- insert into tb_clan_content(tag) values('#2PLJJLY89');
-- insert into tb_clan_content(tag) values('#2PUPJ09VP');
-- insert into tb_clan_content(tag) values('#2LJ0U02YJ');
--
-- insert into TB_CLAN_ASSIGNED_PLAYER values ('#2QJUL08V9', '202405', '#JU0PUPPG');

insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2000PPY8L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#20CCCCQUL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#20CLP09RJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#20LYG8P90', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#20PRYJ9QP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#20Y899PCQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#220J228JL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#220URUJYL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2289Y928R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#228U0Q0UG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2292G0RRQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#229JP8Q8Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#22CQYL08R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#22CVQQJY9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#22JUG882Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#22Q88G099', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#22YJLQJGP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#280GJLYPQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#28220PJR2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#288R292VG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#28G22RUG2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#28QG2VLPV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#299CR8VG8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#29G208RGQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#29JQQY22J', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#29JUR08CG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#29P89LVGC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#29UG9RY20', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#29VQR8RL8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2CPGV988', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2G2PCP9GG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2JCCUYGPQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2JVPR29Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2LR9902QC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2P09800P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2P8URUY0Y', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2PG2PVURY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2QCCG2G0U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2QPGVP9U8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2QUUUYPC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2QVGG0Y0R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2V02J0YY8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2VUJUQC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2Y8QU00YP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2YCPPCG0P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2YPU0JQGJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2YQ98V8YY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2YRGCG28J', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2YURQ2VJG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#2YUULUVV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8020JVLQV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#820JYUQR0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#82Q9JRGU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#88QYGLV9C', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#892CPUPV2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#898CU22Y9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#898VQLCR0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#89CVCQ89U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#89JR2GQYQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8LUCG20L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8PGG09Y2V', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8RU2QRGG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8U9Q20J', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8VPVPLUQP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8VV80LVYR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8Y2VGLCVG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#8YJCPY8YY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#90J2VL222', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#90RC0VR20', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#90YYLPUP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#92J0LYYPU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#99LYRP2JG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9CCLJL0CC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9J8PC289L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9JU2JPUJ2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9JUG9LQC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9JV9LP280', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9P8J092P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9QJPPQJJR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9R9Y00', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9VQUJJQ89', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9Y2C8UYY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9Y9L2YQJ8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#9YJYVLL0L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#C0C822GY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#C8GJG8PC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#C9CU8P2Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#C9QRVUVV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#CGLL9JP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#CPG8QVUU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#CPP8Q2P2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#CR2GLJ82', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G020UL8YV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G02V0UV00', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G0LQLYGGQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G29QL9RQL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G2CU9VLGG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G82YQ8L8G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G8J2CRC8L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G8JPYGLJV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#G9UUP2G8Y', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GC89C9P2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GG0GR0PU0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GG2JJUPV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GP9YU9P2P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GQ8CQR8V', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GRQ2JC2R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GRQLYY09', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GUL2898R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GUQ8R98U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GVLCYU8G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GY8G00RPL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#GYQJYGGL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#J0CVRG9G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#J0GQ0CL2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#J0UR8CPG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#J8RL282V', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#J8YPRYV0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JJ8V2PYJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JJL9YGLL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JQ9JYPJ8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JU0PUPPG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JUCL0P82', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JYJJVYYR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#JYLPGJ0P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#L0JJCP2UU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#L0LUY000J', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#L2GJC9CJQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#L9UGJY0RV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#L9YPRQGPY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LCVVGGLPP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LJ90P8J9Y', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LJLULU9R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LL2VYU9U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LL8PPPCCP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LLUV88JVR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LP9V0VL08', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LPRJG9CV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LPRLQ9YL9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LR0VU0RJQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LRCCL9LLL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LRJRLPR2U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LRVJUUUP8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LUL0RLGR8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LVQLLR88P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LVYV8CUU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#LYJVQU8L9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#P2G90J0VU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#P80YRPJCJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#P89Y2RU9L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#P8GRPG99L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PC2JUQLL8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PJ020Q8G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PJ2LJCVQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PL8909CYV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PPLUG999J', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PRRVC8VC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PRURJCYG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#PYUYJ0VJY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q0GPJYYGQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q20LVVVR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q222PCVQJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q2C0LUY92', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q2V09UL2Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q2Y8P8R9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q82C00PYR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q82PQLQR2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q88LRJPGG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q8J2GUCPY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q8PQPQ0P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q8RGV9LGV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q98RYCLLV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q998JJUYJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q9GPRCL2P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q9GYU9G9Y', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Q9VQ9Y0RC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QC09QUY0Y', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QCY2JQQLG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QG99VY2C0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QG9PC8LYR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QG9YGV0Y9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QGG8QV0JC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QGUP2G2V', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QGYPQR9L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QJCLUQ0LL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QJJRPPGCR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QJV9VGJ8J', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QL88PRJ2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QLL92QL9L', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QLU9G0P0G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QP009JPVQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QPGYVQY8Y', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QPP2GPP00', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QPU9U0PVR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QPVGVCJGR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QQ9CPGG82', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QQGP2YRJR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QQVJUYPGP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QR8JQR89', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QR9G2UR9R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QRCRQVGP0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QRJ0VRGY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QRL9PU82Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QUG2G0C9U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QV2R0LG2U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QV88LPV22', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QVCL9VYP8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QVL228CRC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QVLQ9J9YJ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QVRJJVL8P', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QY800UPQP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QYJ29JUV9', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#QYV8UGRY0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#R908Y09C', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#R9RRQUYP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#RCGLJLVL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#RGCGQ98C', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#RJQJRR2G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#U088U9Q0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#ULCGRQVL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#URUVPGUP', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#UULGGGRU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#V0CL82Q2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#V0RJ0CU8', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#V2R98Y0V', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#V2RP8LPY', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#VG0YPQ89', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#VL2PGC2', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#VR8V9VVU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#VV08JCVU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#VV0PCYLL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y09P0V0JC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y229RCRC0', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y8CR82PVV', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y8G90QGYR', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y8PY8RU2Q', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y8Q0CUYPL', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y8RLYQYRG', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#Y9V2J09R', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#YG8VQR9G', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#YGVPC2Y9U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#YP2RGCGRU', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#YQ9LRGYCC', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#YV8QL0ULQ', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());
insert into tb_player(player_tag, name, ATTACK_WINS, DEFENSE_WINS, TROPHIES, BEST_TROPHIES, WAR_STARS, EXP_LEVEL, TOWN_HALLL_LEVEL, CREATED_DATE, LAST_MODIFIED_DATE) values ('#YYVQ2R88U', 'test', 1, 1, 1, 1, 1, 1, 1, now(), now());