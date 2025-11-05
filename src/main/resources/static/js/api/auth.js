const PREFIX_AUTH_API = '/api/auth' // 인증 API

const URI_VERIFY_CODE = `${PREFIX_AUTH_API}/verify/{code}` // 코드 인증 API

const TOKEN_KEY = "token";
const TOKEN_TTL = 1000 * 60 * 10;

class Token {
  constructor(mode = "unknown", expiredAt) {
    this.mode = mode;
    this.expiredAt = expiredAt;
  }

  isAdmin() {
    return this.mode === 'admin';
  }
  expired() {
    return Date.now() > this.expiredAt;
  }

  static fromStorage(data) {
    const parsed = JSON.parse(data);
    return new Token(parsed.mode, parsed.expiredAt);
  }

  toJSON() {
    return JSON.stringify({ mode: this.mode, expiredAt: this.expiredAt });
  }
}


function initAdminActivate(targetClassName) {
  const target = document.querySelector(`.${targetClassName}`);
  let clickCount = 0;
  let clickTimer;
  target.addEventListener('click', () => {
    clickCount++;
    if (clickTimer) clearTimeout(clickTimer);
    clickTimer = setTimeout(() => clickCount = 0, 2000); // 2초 이내
    if (clickCount >= 5) {
      requiredActivate();
      clickCount = 0;
    }
  })
}

function requiredActivate() {
  const code = prompt("활성화 코드를 입력해주세요.");
  if (!code) return;

  verifySecretCode(code)
  .then((result) => {
    if (!result) return;

    window.location.reload();
  });
}

function getToken() {
  let token = sessionStorage.getItem(TOKEN_KEY);
  if (!token) return new Token();

  token = Token.fromStorage(token);
  if (token.expired()) {
    sessionStorage.removeItem(TOKEN_KEY);
    return new Token();
  }

  return token;
}
function issueToken() {
  const token = new Token("admin", Date.now() + TOKEN_TTL);
  sessionStorage.setItem(TOKEN_KEY, token.toJSON());
}

async function verifySecretCode(code) {
  const uri = `${URI_VERIFY_CODE.replace(/{code}/, encodeURIComponent(code))}`

  return await axios.post(uri)
                    .then((response) => {
                      const isValid = response.data;
                      if (isValid) {
                        issueToken();
                        return true;
                      }

                      alert("인증 실패");
                      return false;
                    })
                    .catch((error) => {
                      console.error(error);

                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);
                      return false;
                    });
}