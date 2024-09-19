const PREFIX_COMPETITION_API = '/api/competitions' // 대회 API

const URI_COMPETITIONS = `${PREFIX_COMPETITION_API}` // 등록된 대회 목록 조회
const URI_COMPETITION_PARTICIPANT = `${PREFIX_COMPETITION_API}/{COMPETITION_ID}/participate/{CLAN_TAG}` // 대회 참가 신청

async function fetchCompetitions() {
  return await axios.get(URI_COMPETITIONS)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function createCompetition(requestBody) {
  return axios.post(URI_COMPETITIONS, requestBody)
              .then(response => {
                const { data } = response
                let message = `대회 정보를 등록했습니다.`
                alert(message);
                return true;
              })
              .catch((error) => {
                console.error(error);

                const { status, response } = error
                if (status === 400) {
                  alert(response.data);
                }
                return false;
              });
}

async function participantCompetition(competitionId, clanTag) {
  const uri = URI_COMPETITION_PARTICIPANT.replace(/{COMPETITION_ID}/, competitionId)
                                         .replace(/{CLAN_TAG}/, encodeURIComponent(clanTag))

  return await axios.post(uri)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      throw error;
                    });
}