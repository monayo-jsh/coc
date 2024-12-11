const PREFIX_SEASON_API = '/api/season' // 시즌 API

const URI_LATEST_SEASONS = `${PREFIX_SEASON_API}/latest` // 최근 시즌 종료일 목록 조회
const URI_SEASON_END_DATE_CREATE = `${PREFIX_SEASON_API}/{endDate}` // 시즌종료일 설정
const URI_SEASON_END_DATE_DELETE = `${PREFIX_SEASON_API}/{endDate}` // 시즌종료일 삭제

async function fetchLatestSeasons() {
  return await axios.get(URI_LATEST_SEASONS)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    })
}

async function createSeasonEndDate(endDate) {
  const uri = URI_SEASON_END_DATE_CREATE.replace(/{endDate}/, endDate)
  return await axios.post(uri)
                    .then((response) => {
                      alert("신규 시즌 종료일을 설정하였습니다.");
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

async function deleteSeasonEndDate(endDate) {
  const uri = URI_SEASON_END_DATE_DELETE.replace(/{endDate}/, endDate)
  return await axios.delete(uri)
                    .then((response) => {
                      alert("제거되었습니다.");
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