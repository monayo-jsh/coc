function renderPage(uri) {
  location.href = uri;
}

function showLoading() {
  const spinIcon = document.querySelector('button.refresh');
  if (spinIcon) {
    spinIcon.classList.add('ing');
  }
}
function hideLoading() {
  const spinIcon = document.querySelector('button.refresh');
  if (spinIcon) {
    spinIcon.classList.remove('ing');
  }
}