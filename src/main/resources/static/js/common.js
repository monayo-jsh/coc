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