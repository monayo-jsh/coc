const PREFIX_LAB_API = '/api/lab' // 연구소 API

const URI_LABORATORIES = `${PREFIX_LAB_API}/links` // 연구소 목록 조회
const URI_LABORATORY_DELETE = `${PREFIX_LAB_API}/{id}` // 연구소 삭제

const URI_LABORATORY_ENTER_CODE = `${PREFIX_LAB_API}/enter-code/{enterCode}` // 연구소 입장코드 문구 설정

async function fetchLaboratories() {
  return await axios.get(URI_LABORATORIES)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function createLaboratory(requestBody) {
  return await axios.post(PREFIX_LAB_API, requestBody)
                    .then((response) => {
                      const { data } = response
                      alert('등록 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);

                      return false;
                    });
}

async function deleteLaboratory(id) {
  const uri = URI_LABORATORY_DELETE.replace(/{id}/, id);
  return await axios.delete(uri)
                    .then((response) => {
                      const { data } = response
                      alert('삭제 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);
                      return false;
                    });
}

async function fetchLatestLaboratoryEnterCode() {
  const uri = URI_LABORATORY_ENTER_CODE.replace(/{enterCode}/, "latest");
  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return undefined;
                    });
}

async function updateLaboratoryEnterCode(code) {
  const uri = URI_LABORATORY_ENTER_CODE.replace(/{enterCode}/, code);
  return await axios.post(uri)
                    .then((response) => {
                      const { data } = response
                      alert('설정 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);
                      return false;
                    });
}