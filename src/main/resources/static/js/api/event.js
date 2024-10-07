const URI_EVENT = '/api/event'; //이벤트 API

const URI_TEAM_LEGEND_LATEST = `${URI_EVENT}/team/legend/latest`; //팀 전설내기 최신 이벤트 조회

async function fetchLatestTeamLegendEvent() {
  return await axios.get(URI_TEAM_LEGEND_LATEST)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}