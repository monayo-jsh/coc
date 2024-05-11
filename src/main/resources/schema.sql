/* 클랜 */
DROP TABLE IF EXISTS TB_CLAN;
CREATE TABLE TB_CLAN(
    TAG VARCHAR(100),
    NAME VARCHAR(100) NOT NULL,
    ORDERS INT NOT NULL,
    REG_DATE TIMESTAMP NOT NULL,
    PRIMARY KEY (TAG)
);

/* 클랜 컨텐츠 관리 */
DROP TABLE IF EXISTS TB_CLAN_CONTENT;
CREATE TABLE TB_CLAN_CONTENT (
    TAG VARCHAR(100),
    CLAN_WAR_YN varchar(1) default 'N' not null,
    WAR_LEAGUE_YN varchar(1) default 'N' not null,
    CLAN_CAPITAL_YN varchar(1) default 'N' not null,
    CLAN_WAR_PARALLEL_YN varchar(1) default 'N' not null,
    PRIMARY KEY (TAG)
);

/* 클랜 배정 유저 관리 */
DROP TABLE IF EXISTS TB_CLAN_ASSIGNED_PLAYER;
CREATE TABLE TB_CLAN_ASSIGNED_PLAYER (
    CLAN_TAG VARCHAR(100),
    SEASON_DATE VARCHAR(6),
    PLAYER_TAG VARCHAR(100),
    PRIMARY KEY (CLAN_TAG, SEASON_DATE, PLAYER_TAG)
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

-- insert into TB_CLAN_ASSIGNED_PLAYER values ('#2QJUL08V9', '202405', '#JU0PUPPG');