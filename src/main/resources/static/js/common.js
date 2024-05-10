function renderPage(uri) {
  location.href = uri;
}

function launchAppWithOpenClanProfile(tag) {
  if (!confirm("게임을 실행하시겠습니까?")) {
    return;
  }

  location.href = `clashofclans://action=OpenClanProfile&tag=${tag}`;
}

function addClassDim(element) {
  element.classList.add('dim')
}
function showWifiLoading(dimElementClassName) {
  setTimeout(() => {
    const cardContainer = document.querySelector(dimElementClassName);
    cardContainer.classList.add('opacity-01');
    const loader = document.querySelector('#wifi-loader');
    loader.classList.remove('display-none');
  }, 100)
}

function hideWifiLoading(dimElementClassName) {
  setTimeout(() => {
    const cardContainer = document.querySelector(dimElementClassName);
    cardContainer.classList.remove('opacity-01');
    const loader = document.querySelector('#wifi-loader');
    loader.classList.add('display-none');
  }, 100)
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

function convClanJoinTypeName(type) {
  switch (type.toLowerCase()) {
    case 'open': return '공개';
    case 'inviteonly': return '초대 한정';
    case 'closed': return '비공개'
  }
  return type;
}