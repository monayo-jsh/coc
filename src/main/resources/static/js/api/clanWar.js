const URI_CLAN_WARS = "/v2/api/clan/war/period" //클랜전 목록 기간 조회
const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_PERIOD = "/v2/api/clan/war/missing/attack/period" //클랜전 미공 사용자 목록 기간 조회
const URI_CLAN_WAR_DETAIL = "/v2/api/clan/war/{warId}" //클랜전 상세 조회
const URI_CLAN_WAR_MEMBERS = "/v2/api/clan/war/members" //클랜전 참여 계정 목록 조회
const URI_RANKING_CLAN_WAR_STARS = "/api/clan/war/ranking/stars" //월 클랜전 획득별 순위

const URI_RANKING_CLAN_LEAGUE_WAR_STARS = "/api/clan/war/league/ranking/stars" //월 리그전 획득별 순위

const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS = "/v2/api/clan/war/missing/attack"

const URI_CLAN_WAR_MEMBER_NECESSARY_ATTACK = "/v2/api/clan/war/{warId}/{playerTag}/necessary"

async function fetchClanWars(startDate, endDate) {
  const uri = URI_CLAN_WARS + `?startDate=${startDate}&endDate=${endDate}`;
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

async function fetchClanWarMissingAttackPlayers(startDate, endDate) {
  const uri = URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_PERIOD + `?startDate=${startDate}&endDate=${endDate}`;
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

async function fetchMissingAttacksByTag(playerTag) {
    return await axios.get(URI_CLAN_WAR_MISSING_ATTACK_PLAYERS, {
          params: {
            tag: playerTag
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

async function fetchMissingAttacksByName(playerName) {
    return await axios.get(URI_CLAN_WAR_MISSING_ATTACK_PLAYERS, {
        params: {
            name: playerName
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

async function fetchClanWarMembers(clanTag, startTime, necessaryAttackYn) {
  let URI = URI_CLAN_WAR_MEMBERS + `?clanTag=${encodeURIComponent(clanTag)}&startTime=${startTime}`
  if (necessaryAttackYn) {
    URI += `&necessaryAttackYn=${necessaryAttackYn}`
  }
  return await axios.get(URI)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}