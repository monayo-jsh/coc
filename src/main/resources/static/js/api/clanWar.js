const URI_CLAN_WARS = "/api/clan/war/period/{searchMonth}" //클랜전 목록 기간 조회
const URI_CLAN_WAR_DETAIL = "/api/clan/war/{warId}" //클랜전 상세 조회
const URI_RANKING_CLAN_WAR_STARS = "/api/clan/war/ranking/stars" //월 클랜전 획득별 순위

const URI_RANKING_CLAN_LEAGUE_WAR_STARS = "/api/clan/war/league/ranking/stars" //월 리그전 획득별 순위


async function fetchClanWars(searchMonth) {
  const uri = URI_CLAN_WARS.replace(/{searchMonth}/, encodeURIComponent(searchMonth));
  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchClanWarDetail(warId) {
  const uri = URI_CLAN_WAR_DETAIL.replace(/{warId}/, warId);
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

async function fetchRankingClanWarStars(searchMonth, clanTag) {
  const uri = URI_RANKING_CLAN_WAR_STARS + `?searchMonth=${searchMonth}&clanTag=${encodeURIComponent(clanTag)}`;
  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingClanLeagueWarStars(searchMonth, clanTag) {
  const uri = URI_RANKING_CLAN_LEAGUE_WAR_STARS + `?searchMonth=${searchMonth}&clanTag=${encodeURIComponent(clanTag)}`;
  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}