const URI_RAID_CLAN_CAPITAL_SEASON = '/raid/{clanTag}/seasons'; //클랜 습격전 최근 시즌 조회
const URI_RAID_SCORE = '/raid/score/{playerTag}'; //멤버 습격전 기록 조회

async function fetchPlayerRaidScore(playerTag) {
  const uri = URI_RAID_SCORE.replace(/{playerTag}/, encodeURIComponent(playerTag));

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