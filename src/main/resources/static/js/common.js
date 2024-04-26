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