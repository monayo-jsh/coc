const PREFIX_AUTH_API = '/api/auth' // 인증 API

const URI_VERIFY_CODE = `${PREFIX_AUTH_API}/verify/{code}` // 코드 인증 API

async function verifySecretCode(code) {
  const uri = `${URI_VERIFY_CODE.replace(/{code}/, encodeURIComponent(code))}`

  return await axios.post(uri)
                    .then((response) => {
                      const isValid = response.data;
                      if (isValid) {
                        sessionStorage.setItem("mode", "admin");
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