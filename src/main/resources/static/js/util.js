function filterVillage(array, village = 'home') {
  return array.filter(data => data.village === village);
}

function convertToKorean(name) {
  switch (name) {
    // 영웅
    case 'Barbarian King': return "바바리안 킹";
    case 'Archer Queen': return "아처 퀸";
    case 'Grand Warden': return "그랜드 워든";
    case 'Royal Champion': return "로얄 챔피언";

    // 장비
    // 바바리안 킹
    case 'Barbarian Puppet': return "바바리안 인형";
    case 'Earthquake Boots': return "지진 부츠";
    case 'Giant Gauntlet': return "자이언트 건틀릿";
    case 'Rage Vial': return "분노 마법 병";
    case 'Vampstache': return "흡혈 수염";

    // 아처 퀸
    case 'Archer Puppet': return "아처 인형";
    case 'Giant Arrow': return "자이언트 화살";
    case 'Frozen Arrow': return "얼음 화살";
    case 'Invisibility Vial': return "투명 마법 병";
    case 'Healer Puppet': return "치유사 인형";

    // 그랜드 워든
    case 'Life Gem': return "생명의 보석";
    case 'Rage Gem': return "분노 보석";
    case 'Fireball': return "파이어 볼";
    case 'Eternal Tome': return "영원의 책";
    case 'Healing Tome': return "치유의 책";

    // 로얄 챔피언
    case 'Royal Gem': return "로얄 보석";
    case 'Haste Vial': return "신속 마법 병";
    case 'Seeking Shield': return "추적 방패";
    case 'Hog Rider Puppet': return "호그 라이더 인형";

    // 펫
    case 'L.A.S.S.I': return "L.A.S.S.I";
    case 'Electro Owl': return "일렉트로 아울";
    case 'Mighty Yak': return "마이트 야크";
    case 'Unicorn': return "유니콘";
    case 'Frosty': return "프로스티";
    case 'Diggy': return "디기";
    case 'Poison Lizard': return "독마뱀";
    case 'Phoenix': return "피닉스";
    case 'Spirit Fox': return "스피릿 폭스";
    case 'Angry Jelly': return "앵그리 젤리";
  }

  return name;
}

function convertArrayToMap(array) {
  return filterVillage(array).reduce((map, row) => {
    map[convertToKorean(row.name)] = row.level
    return map
  }, {});
}

function formattedPlayers(players) {
  return players.map(player => {
    const heroMap = convertArrayToMap(player.heroes);
    const heroEquipmentMap = convertArrayToMap(player.heroEquipments);
    const petMap = convertArrayToMap(player.pets);
    return {
      '클랜': player.clan.name,
      '이름': player.name,
      '태그': player.tag,
      '타운홀': player.townHallLevel,
      '경험치레벨': player.expLevel,
      '현재 트로피': player.trophies,
      '최고 트로피': player.bestTrophies,
      '전쟁획득별': player.warStars,
      '지원수': player.donations,
      '지원받은수': player.donationsReceived,
      '시즌 공격수': player.attackWins,
      '시즌 방어수': player.defenseWins,
      '영웅합': player.heroLevelSum,
      ...heroMap,
      ...heroEquipmentMap,
      ...petMap,
    };
  })
}

/**
 * 엑셀 다운로드 기능
 * @param members
 */
function exportExcel(members) {
  const players = formattedPlayers(members);

  const workbook = XLSX.utils.book_new();
  const worksheet = XLSX.utils.json_to_sheet(players);
  XLSX.utils.book_append_sheet(workbook, worksheet, "Dates");
  const fileName = `클랜원_${dayjs().format('YYYYMMDDHHmmss')}.xlsx`;
  XLSX.writeFile(workbook, fileName);
}

/**
 * 클랜 권한 한글명 반환
 * @param role
 * @returns {*|string}
 */
function convName(role) {
  if (!role) return role;
  switch (role.toUpperCase()) {
    case 'LEADER': return '대표';
    case 'COLEADER': return '공동대표'
    case 'ADMIN': return '장로';
    case 'MEMBER': return '일반';
    default: return role;
  }
}