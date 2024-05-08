function renderPage(uri) {
  location.href = uri;
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