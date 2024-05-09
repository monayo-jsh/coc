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
    content: tooltip
  });
}