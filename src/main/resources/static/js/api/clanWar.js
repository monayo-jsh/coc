const URI_CLAN_WARS = "/api/clan/war/period" //클랜전 목록 기간 조회
const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS = "/api/clan/war/missing/attack/period" //클랜전 미공 사용자 목록 기간 조회
const URI_CLAN_WAR_DETAIL = "/api/clan/war/{warId}" //클랜전 상세 조회
const URI_RANKING_CLAN_WAR_STARS = "/api/clan/war/ranking/stars" //월 클랜전 획득별 순위

const URI_RANKING_CLAN_LEAGUE_WAR_STARS = "/api/clan/war/league/ranking/stars" //월 리그전 획득별 순위

const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_IN_LAST_90_DAYS_WITH_NAME = "/api/clan/war/missing/attack/playerName"
const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_IN_LAST_90_DAYS_WITH_TAG = "/api/clan/war/missing/attack/{playerTag}"

const URI_CLAN_WAR_MEMBER_NECESSARY_ATTACK = "/api/clan/war/{warId}/{playerTag}/necessary"

async function fetchClanWars(startDt, endDt) {
  const uri = URI_CLAN_WARS + `?startDate=${encodeURIComponent(startDt)}&endDate=${encodeURIComponent(endDt)}`;
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

async function fetchClanWarMissingAttackPlayers(startDt, endDt) {
  const uri = URI_CLAN_WAR_MISSING_ATTACK_PLAYERS + `?startDate=${encodeURIComponent(startDt)}&endDate=${encodeURIComponent(endDt)}`;
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

async function fetchMissingAttackPlayerInLast90DaysWithTag(playerTag) {
    const uri = URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_IN_LAST_90_DAYS_WITH_TAG
        .replace(/{playerTag}/, encodeURIComponent(playerTag));

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

async function fetchMissingAttackPlayerInLast90DaysWithName(playerName) {

    return await axios.get(URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_IN_LAST_90_DAYS_WITH_NAME, {
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

async function fetchRankingClanWarStars(searchMonth, clanTag, searchType) {
  let uri = URI_RANKING_CLAN_WAR_STARS + `?searchMonth=${searchMonth}&clanTag=${encodeURIComponent(clanTag)}`;
  if (searchType) {
    uri += `&searchType=${searchType}`
  }
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

async function fetchRankingClanLeagueWarStars(searchMonth, clanTag, searchType) {
  let uri = URI_RANKING_CLAN_LEAGUE_WAR_STARS + `?searchMonth=${searchMonth}&clanTag=${encodeURIComponent(clanTag)}`;
  if (searchType) {
    uri += `&searchType=${searchType}`
  }
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

async function putClanWarNecessaryAttack(warId, playerTag) {
  const URI = URI_CLAN_WAR_MEMBER_NECESSARY_ATTACK.replace(/{warId}/, warId).replace(/{playerTag}/, encodeURIComponent(playerTag));
  return await axios.put(URI)
                    .then((response) => {
                      alert('처리 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);

                      console.error(error);
                      return false;
                    })
}