const URI_RAID_CLAN_CAPITAL_SEASON_COLLECT = '/raid/collect/seasons'; //클랜 습격전 최근 시즌 데이터 수집

const URI_RAID_CLAN_CAPITAL_SEASON = '/raid/{clanTag}/seasons'; //클랜 습격전 최근 시즌 조회
const URI_RAID_SCORE_WITH_TAG = '/raid/score/{playerTag}'; //멤버 습격전 기록 조회(태그)
const URI_RAID_SCORE_WITH_NAME = '/raid/score/playerName'; // 멤버 습격전 기록 조회(이름)

async function collectRaidSeason() {
  return axios.post(URI_RAID_CLAN_CAPITAL_SEASON_COLLECT)
              .then((response) => {
                return response;
              })
              .catch((error) => {
                console.error(error);
                return {};
              })
}
async function fetchPlayerRaidScoreWithPlayerTag(playerTag) {
  const uri = URI_RAID_SCORE_WITH_TAG.replace(/{playerTag}/, encodeURIComponent(playerTag));

  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchPlayerRaidScoreWithPlayerName(playerName) {

    return await axios.get(URI_RAID_SCORE_WITH_NAME, {
        params: {
            playerName: playerName
        }
    })
        .then((response) => {
            const { data } = response;
            return data;
        })
        .catch((error) => {
            console.error(error);
            return [];
        })
}

async function fetchClanCapitalRaidSeason(clanTag) {
  const uri = URI_RAID_CLAN_CAPITAL_SEASON.replace(/{clanTag}/, encodeURIComponent(clanTag));
  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);

                      const { code } = error;
                      if (code === 'ERR_NETWORK') {
                        return {};
                      }

                      const { response } = error;
                      const { status } = response
                      // 서버 이용 불가로 메인 화면 이동
                      if (status >= 500) {
                        location.href = "/";
                      }

                      return {};
                    });
}