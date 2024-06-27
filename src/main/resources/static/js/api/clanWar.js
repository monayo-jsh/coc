const URI_RANKING_CLAN_WAR_STARS = "/api/clan/war/ranking/stars" //월 클랜전 획득별 순위

async function fetchRankingClanWarStars(searchMonth) {
  const uri = URI_RANKING_CLAN_WAR_STARS + `?searchMonth=${searchMonth}`;
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