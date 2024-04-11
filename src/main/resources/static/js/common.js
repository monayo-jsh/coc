function renderPage(uri) {
  location.href = uri;
}

function getTbody() {
  return getButtonRefresh()
                 .parentElement
                 .parentElement
                 .querySelector('table tbody.contents');
}

function getButtonRefresh() {
  return document.querySelector('button.refresh');
}

function showLoading() {
  const spinIcon = getButtonRefresh();
  if (spinIcon) {
    spinIcon.classList.add('ing');

    const tbody = getTbody();
    tbody.classList.add('loading');
  }
}
function hideLoading() {
  const spinIcon = getButtonRefresh();
  if (spinIcon) {
    spinIcon.classList.remove('ing');

    const tbody = getTbody();
    tbody.classList.remove('loading');
  }
}