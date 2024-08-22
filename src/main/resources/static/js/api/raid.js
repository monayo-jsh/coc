const PREFIX_RAID_API = '/api/raid' // 클랜 캐피탈 API

// 현재 시즌 관련 엔드포인트
const URI_RAID_SEASON = `${PREFIX_RAID_API}/{clanTag}/seasons/current`; // 특정 클랜 캐피탈 이번 시즌 조회
const URI_RAID_SEASON_ATTACKS = `${PREFIX_RAID_API}/seasons/current/attacks`; // 클랜 캐피탈 이번 시즌 공격 기록 조회
const URI_RAID_SEASON_COLLECT = `${PREFIX_RAID_API}/seasons/current/collect`; //클랜 캐피탈 이번 시즌 데이터 수집 요청

// 플레이어 기록 관련 엔드포인트
const URI_RAID_SCORE_PLAYER = `${PREFIX_RAID_API}/score`; // 클랜 캐피탈 플레이어 기록 조회

// 랭킹 관련 엔드포인트
const URI_RAID_SEASON_CURRENT_RANKING = `${PREFIX_RAID_API}/seasons/current/ranking`; // 클랜 캐피탈 현재 시즌 랭킹
const URI_RAID_SEASON_AVERAGE_RANKING = `${PREFIX_RAID_API}/seasons/average/ranking`; // 클랜 캐피탈 시즌 평균 랭킹

async function collectRaidSeason() {
  return axios.post(URI_RAID_SEASON_COLLECT)
              .then((response) => {
                return response;
              })
              .catch((error) => {
                console.error(error);
                return {};
              })
}
async function fetchPlayerRaidScoreWithPlayerTag(playerTag) {
  const option = {
    params: {
      playerTag
    }
  }

  return await axios.get(URI_RAID_SCORE_PLAYER, option)
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
  const option = {
    params: {
      playerName
    }
  }
  return await axios.get(URI_RAID_SCORE_PLAYER, option)
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
  const uri = URI_RAID_SEASON.replace(/{clanTag}/, encodeURIComponent(clanTag));
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

                      return {};
                    });
}

async function fetchRaidAttackSeasonPlayers() {
  return await axios.get(URI_RAID_SEASON_ATTACKS)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingRaidCurrentSeason() {
  return await axios.get(URI_RAID_SEASON_CURRENT_RANKING)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingRaidAverageSeason() {
  return await axios.get(URI_RAID_SEASON_AVERAGE_RANKING)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}