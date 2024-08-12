function removeHashTag(playerTag) {
  return playerTag.replaceAll("#", "");
}

function formatTime(time, format="HH:mm") {
  // import 필수
  // <script src="https://cdnjs.cloudflare.com/ajax/libs/dayjs/1.11.10/plugin/duration.min.js" integrity="sha512-t0b2NyBypSms8nA81pldWo0mXbfMotsdYgvs4awmbi/GU/25YBNLnXj+I9DAMV7WGZ8+z8wtRolX7zSF2LN8UQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  if (dayjs.isDayjs(time)) return time.format(format);
  if (dayjs.isDuration(time)) return time.format(format);

  return dayjs(time).format(format)
}

function formatMinuteSecond(sec) {
  // import 필수
  // <script src="https://cdnjs.cloudflare.com/ajax/libs/dayjs/1.11.10/plugin/duration.min.js" integrity="sha512-t0b2NyBypSms8nA81pldWo0mXbfMotsdYgvs4awmbi/GU/25YBNLnXj+I9DAMV7WGZ8+z8wtRolX7zSF2LN8UQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  return dayjs.duration(sec * 1000).format('m분 ss초');
}

function getLeftUntilByTime(endTime) {
  const now = dayjs();
  const endDateTime = dayjs(endTime);

  if (now > endDateTime) {
    return "";
  }

  return dayjs.duration(endDateTime.diff(now));
}

function filterVillage(array, village = 'home') {
  return array.filter(data => data.village === village);
}

function convertArrayToLevelMapByKoreanName(array) {
  return filterVillage(array).reduce((map, row) => {
    const {koreanName, level} = row;
    map[koreanName] = level
    return map
  }, {});
}

function convertArrayToMapByKey(array, key = 'tag', isList = false) {
  return array.reduce((map, row) => {
    if (!isList) {
      // 객체
      map.set(row[key], row);
      return map;
    }

    // 배열
    if (!map.get(row[key])) {
      map.set(row[key], []);
    }

    map.get(row[key]).push(row);
    return map;
  }, new Map());
}

function formattedPlayers(players) {
  return players.map(player => {
    const heroMap = convertArrayToLevelMapByKoreanName(player.heroes);
    const heroEquipmentMap = convertArrayToLevelMapByKoreanName(player.heroEquipments);
    const petMap = convertArrayToLevelMapByKoreanName(player.pets);
    return {
      '클랜': player.clan ? player.clan.name : '',
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
      '영웅합': player.heroTotalLevel,
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
  // 이름순
  members = members.sort((a, b) => a.name.localeCompare(b.name) || b.heroTotalLevel - a.heroTotalLevel);
  const players = formattedPlayers(members);
  const fileName = `클랜원_${dayjs().format('YYYYMMDDHHmmss')}.xlsx`;
  writeExcelFile(fileName, players)
}

function writeExcelFile(fileName, jsonData) {
  const workbook = XLSX.utils.book_new();
  const worksheet = XLSX.utils.json_to_sheet(jsonData);
  XLSX.utils.book_append_sheet(workbook, worksheet, "Dates");
  XLSX.writeFile(workbook, fileName);
}

/**
 * 클랜 권한 한글명 반환
 * @param role
 * @returns {*|string}
 */
function convRoleName(role) {
  if (!role) return role;
  switch (role.toUpperCase()) {
    case 'LEADER': return '대표';
    case 'COLEADER': return '공동대표'
    case 'ADMIN': return '장로';
    case 'MEMBER': return '일반';
    default: return role;
  }
}

function settingTooltip(element, tooltip) {
  // tooltip 설정
  // 사용하는 페이징 상단 라이브러리 추가 필요함
  // <script src="https://unpkg.com/@popperjs/core@2"></script>
  // <script src="https://unpkg.com/tippy.js@6"></script>
  tippy(element, {
    content: tooltip,
    animation: 'fade',
    theme: 'darkgray',
    arrow: false
  });
}

function convLeagueName(leagueName) {
  switch (leagueName) {
    case 'Unranked': return '랭크되지 않음';
    case 'Bronze League III': return '브론즈 리그 III';
    case 'Bronze League II': return '브론즈 리그 II';
    case 'Bronze League I': return '브론즈 리그 I';
    case 'Silver League III': return '실버 리그 III';
    case 'Silver League II': return '실버 리그 II';
    case 'Silver League I': return '실버 리그 I';
    case 'Gold League III': return '골드 리그 III';
    case 'Gold League II': return '골드 리그 II';
    case 'Gold League I': return '골드 리그 I';
    case 'Crystal League III': return '크리스탈 리그 III';
    case 'Crystal League II': return '크리스탈 리그 II';
    case 'Crystal League I': return '크리스탈 리그 I';
    case 'Master League III': return '마스터 리그 III';
    case 'Master League II': return '마스터 리그 II';
    case 'Master League I': return '마스터 리그 I';
    case 'Champion League III': return '챔피언 리그 III';
    case 'Champion League II': return '챔피언 리그 II';
    case 'Champion League I': return '챔피언 리그 I';
    case 'Titan League III': return '타이탄 리그 III';
    case 'Titan League II': return '타이탄 리그 II';
    case 'Titan League I': return '타이탄 리그 I';
    case 'Legend League': return '전설 리그';
  }
  return leagueName;
}

function convertNameByType(type) {
  if (type === 'ELIXIR') return '일반';
  if (type === 'DARK_ELIXIR') return '암흑';
  return type;
}

function convClanJoinTypeName(type) {
  switch (type.toLowerCase()) {
    case 'open': return '공개';
    case 'inviteonly': return '초대 한정';
    case 'closed': return '비공개'
  }
  return type;
}

function makeMapByTag(objs) {
  return objs.reduce((map, obj) => {
    map[obj.tag] = obj;
    return map
  }, {});
}

function calcHeroLevelSum(heroes) {
  if (!heroes) return 0;
  return filterVillage(heroes).reduce((levelSum, hero) => {
    levelSum += hero.level
    return levelSum
  }, 0)
}

function sortByTrophies(members) {
  // 트로피 순 > 이름 순
  return members.map(member => member)
                .sort((a, b) => b.trophies - a.trophies || b.name.localeCompare(a.name));
}

function sortByHeroTotalLevel(players) {
  // 영웅레벨 순 -> 이름 순
  return players.sort((a, b) => b.heroTotalLevel - a.heroTotalLevel || a.name.localeCompare(b.name));
}

function sortedByOrder(arrays) {
  // order 필드에 따른 정렬
  return arrays.sort((a, b) => a.order - b.order);
}

function formatYYMMDate(date) {
  const formattedDate = dayjs(date).format('YY년 MM월');
  return `[${formattedDate}] `;
}

function formatYYMMDD(date) {
  return dayjs(date).format('YY-MM-DD');
}

function formatYYYYMMDD(date) {
  return dayjs(date).format('YYYY-MM-DD');
}

function formatDD(date) {
  return dayjs(date).format('DD');
}

function formatYYYYMMDDHHMM(date) {
  return dayjs(date).format('YYYY-MM-DD HH:mm');
}

function toastMessage(message){
  if (!Toastify) return;

  Toastify({
    text: message,
    duration: 3000,
    close: true,
    gravity: "top", // `top` or `bottom`
    position: "right", // `left`, `center` or `right`
    stopOnFocus: true, // Prevents dismissing of toast on hover
    className: "custom",
    style: {
      background: "linear-gradient(to right, #00b09b, #96c93d)",
    },
    onClick: function(){} // Callback after click
  }).showToast();
}
function notifyMessage(message) {
  // Let's check if the browser supports notifications
  if (!("Notification" in window)) {
    alert(message);
  }

  // Let's check whether notification permissions have already been granted
  else if (Notification.permission === "granted") {
    // If it's okay let's create a notification
    var notification = new Notification(message);
  }

  // Otherwise, we need to ask the user for permission
  else if (Notification.permission !== "denied") {
    Notification.requestPermission(function (permission) {
      // If the user accepts, let's create a notification
      if (permission === "granted") {
        var notification = new Notification(message);
      }
    });
  }

  // At last, if the user has denied notifications, and you
  // want to be respectful there is no need to bother them any more.
}

function copyClipboard(value, toastMsg = "") {
  toastMsg += " 복사 완료";
  if (window.isSecureContext && navigator.clipboard) {
    window.navigator.clipboard.writeText(value).then(() => {
      toastMessage(toastMsg);
    })
  } else {
    unsecuredCopyToClipboard(value, toastMsg);
  }

  function unsecuredCopyToClipboard(value, toastMsg) {
    //아래와 같은 document.execCommand 방식은 Deprecated 처리 되어
    //clipboard API로 대체 되었으나 해당 방식은 https 상태에서만 동작
    //http 상태에서 동작 할 수 있도록 해당 로직 사용
    const textArea = document.createElement("textarea");
    textArea.value = value;
    document.body.appendChild(textArea);
    textArea.select();
    try {
      document.execCommand('copy');
      toastMessage(toastMsg);
    } catch (err) {
      console.error('Unable to copy to clipboard', err);
    }
    document.body.removeChild(textArea);
  }
}



function validateString(str) {
  if (!str) return false;
  if (str.length < 0) return false;

  for (let i = 0; i < str.length; i++) {
    const char = str.charAt(i);
    if (isHangulIncomplete(char)) {
      return false;
    }
    if (!isValidChar(char)) {
      return false;
    }
  }
  return true;
}

function isHangulIncomplete(char) {
  const charCode = char.charCodeAt(0);
  return (charCode >= 0x1100 && charCode <= 0x11FF) || // 한글 자모 (초성, 중성, 종성)
      (charCode >= 0x3130 && charCode <= 0x318F) || // 한글 호환 자모
      (charCode >= 0xA960 && charCode <= 0xA97F) || // 한글 자모 확장-A
      (charCode >= 0xD7B0 && charCode <= 0xD7FF);   // 한글 자모 확장-B
}

function isValidChar(char) {
  return isKoreanComplete(char) || isEnglish(char) || isDigit(char);
}

function isKoreanComplete(char) {
  const charCode = char.charCodeAt(0);
  return charCode >= 0xAC00 && charCode <= 0xD7A3; // 한글 완성된 글자
}

function isEnglish(char) {
  const charCode = char.charCodeAt(0);
  return (charCode >= 0x0041 && charCode <= 0x005A) || // 영어 대문자
      (charCode >= 0x0061 && charCode <= 0x007A);   // 영어 소문자
}

function isDigit(char) {
  const charCode = char.charCodeAt(0);
  return charCode >= 0x0030 && charCode <= 0x0039; // 숫자
}

function getBgColorByWarType(type = '') {
  switch (type.toLowerCase()) {
    case 'none': return 'bg-green';
    case 'league': return 'bg-crimson';
    case 'parallel': return 'bg-orange';
  }
  return type;
}

function getBgColorByBattleType(type = '') {
  switch (type.toLowerCase()) {
    case 'none': return '';
    case 'hardmode': return 'bg-orange';
  }
  return type;
}

function convBattleTypeName(battleType = 'none') {
  if (battleType === 'none') return '일반 모드';
  return '하드 모드';
}

function convWarTypeName(type) {
  switch (type.toLowerCase()) {
    case 'none': return '클랜전';
    case 'league': return '리그전';
    case 'parallel': return '병행클랜전';
  }
  return type;
}


function makeMonthPickerOption(firstDayOfMonth, customOption = {
  validRange: true
}) {
  // <!-- JavaScript Year and Month Picker -->
  //   <script src="https://jsuites.net/v4/jsuites.js"></script>
  //   <link rel="stylesheet" href="https://jsuites.net/v4/jsuites.css" type="text/css" />
  const option = {
    type: 'year-month-picker',
    format: 'YYYY-MM',
    controls: false,
    readonly: true,
    value: firstDayOfMonth, // default
    months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthsFull: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
  };

  // setting validRange
  if (customOption.validRange) {
    option.validRange = [ firstDayOfMonth ];
  }

  // update callback mappings
  if (customOption.onchange) {
    option.onchange = customOption.onchange;
  }
  return option
}

function isMissingAttack(attacks) {
  const ESSENTIAL_ATTACK_COUNT = 6;
  return attacks < ESSENTIAL_ATTACK_COUNT;
}

function isDemotedAttack(value) {
  const DEMOTED_ATTACK_VALUE = 20000;
  return value < DEMOTED_ATTACK_VALUE;
}

function copyTemplateById(templateId) {
  const template = document.querySelector(`#${templateId}`);
  return document.importNode(template.content, true);
}