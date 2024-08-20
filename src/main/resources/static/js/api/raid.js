const URI_RAID_CLAN_CAPITAL_SEASON_COLLECT = '/raid/collect/seasons'; //클랜 습격전 최근 시즌 데이터 수집

const URI_RAID_CLAN_CAPITAL_SEASON = '/raid/{clanTag}/seasons'; //클랜 습격전 최근 시즌 조회
const URI_RAID_SCORE = '/raid/score'; //멤버 습격전 기록 조회

const URI_RAID_ATTACK_SEASON_PLAYERS = '/raid/attack/season'; // 이번 시즌 기록 조회

const URI_RAID_RANKING_CURRENT_SEASON = '/raid/ranking/current/season'; // 습격전 현재 시즌 랭킹
const URI_RAID_RANKING_AVERAGE_SEASON = '/raid/ranking/average/season'; // 습격전 시즌 평균 랭킹

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
  const option = {
    params: {
      playerTag
    }
  }

  return await axios.get(URI_RAID_SCORE, option)
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
  return await axios.get(URI_RAID_SCORE, option)
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

                      return {};
                    });
}

async function fetchRaidAttackSeasonPlayers() {
  return await axios.get(URI_RAID_ATTACK_SEASON_PLAYERS)
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
  return await axios.get(URI_RAID_RANKING_CURRENT_SEASON)
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
  return await axios.get(URI_RAID_RANKING_AVERAGE_SEASON)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}