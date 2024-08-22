const PREFIX_LEAGUE_API = '/api/leagues' // 리그 API

const URI_LEAGUES = `${PREFIX_LEAGUE_API}` // 리그 목록 조회

async function fetchLeagues() {
  return await axios.get(URI_LEAGUES)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return {};
                    });
}