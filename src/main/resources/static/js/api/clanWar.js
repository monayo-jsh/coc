const PREFIX_CLAN_WAR_API = "/api/clan/war"

const URI_CLAN_WARS = `${PREFIX_CLAN_WAR_API}/period` //클랜전 목록 조회 - 기간
const URI_CLAN_WAR_DETAIL = `${PREFIX_CLAN_WAR_API}/{warId}` //클랜전 상세 정보 조회
const URI_CLAN_WAR_PARTICIPANTS = `${PREFIX_CLAN_WAR_API}/participants` //클랜전 참여 계정 목록 조회

const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS = `${PREFIX_CLAN_WAR_API}/missing/attack` // 클랜전 미공 기록 조회
const URI_CLAN_WAR_MISSING_ATTACK_PLAYERS_PERIOD = `${PREFIX_CLAN_WAR_API}/missing/attack/period` //클랜전 미공 기록 조회 - 기간

const URI_CLAN_WAR_MEMBER_NECESSARY_ATTACK = `${PREFIX_CLAN_WAR_API}/{warId}/{playerTag}/necessary` // 클랜전 참여 계정 강제 참여 여부 설정

const URI_CLAN_WAR_MEMBER_RECORD = `${PREFIX_CLAN_WAR_API}/record` //월 클랜전 기록 순위
const URI_LEAGUE_WAR_MEMBER_RECORD = `${PREFIX_CLAN_WAR_API}/league/record` //월 리그전 기록 순위

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
  let uri = URI_CLAN_WAR_MEMBER_RECORD + `?month=${searchMonth}&clanTag=${encodeURIComponent(clanTag)}`;
  if (searchType) {
    uri += `&type=${searchType}`
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

async function fetchRankingClanLeagueWarStars(searchMonth, clanTag, searchType, isPerfect) {
  let uri = URI_LEAGUE_WAR_MEMBER_RECORD + `?month=${searchMonth}&clanTag=${encodeURIComponent(clanTag)}`;
  if (searchType) {
    // 조회 유형
    uri += `&type=${searchType}`
  }
  if (isPerfect) {
    // 완파 여부
    uri += `&isPerfect=${isPerfect}`
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

async function fetchClanWarParticipants(clanTag, startTime, necessaryAttackYn) {
  let URI = URI_CLAN_WAR_PARTICIPANTS + `?clanTag=${encodeURIComponent(clanTag)}&startTime=${startTime}`
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