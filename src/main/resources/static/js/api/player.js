const PREFIX_PLAYER_API = '/api/players' // 플레이어 API

const URI_PLAYERS_ALL = `${PREFIX_PLAYER_API}`; //전체 클랜원 조회
const URI_PLAYERS_ALL_TAGS = `${PREFIX_PLAYER_API}/tags`; //전체 클랜원 태그 조회

const URI_PLAYERS_OPEN_API = `${PREFIX_PLAYER_API}/{playerTag}/external`; //멤버 조회 (Open API)
const URI_PLAYERS_DETAIL = `${PREFIX_PLAYER_API}/{playerTag}`; //멤버 등록,삭제

const URI_PLAYERS_TAGS_LEGEND_RECORD = '/players/legend/record'; // 플레이어 전설 기록 플레이어 태그 목록 조회
const URI_PLAYERS_LEGEND_RECORD = `${PREFIX_PLAYER_API}/{playerTag}/legend/record`; // 플레이어 전설 기록 조회

const URI_PLAYERS_SUPPORT = `${PREFIX_PLAYER_API}/{playerTag}/support`; //지원 등록/해제
const URI_PLAYERS_SUPPORT_BULK = `${PREFIX_PLAYER_API}/support/bulk`; //지원계정 일괄 등록

const URI_PLAYERS_RANKING_HERO_EQUIPMENTS = `${PREFIX_PLAYER_API}/ranking/hero/equipments`; //영웅 장비 랭킹

const URI_PLAYERS_RANKING_CURRENT_TROPHIES = `${PREFIX_PLAYER_API}/ranking/trophies` //현재 트로피 순위
const URI_PLAYERS_RANKING_ATTACK_WINS = `${PREFIX_PLAYER_API}/ranking/attack/wins` //현재 공성 순위

const URI_PLAYERS_RANKING_DONATIONS = `${PREFIX_PLAYER_API}/ranking/donations` //현재 지원 순위
const URI_PLAYERS_RANKING_DONATIONS_RECEIVED = `${PREFIX_PLAYER_API}/ranking/donations/received` //현재 지원 받은 순위

async function findPlayerFromExternal(playerTag) {
  const uri = URI_PLAYERS_OPEN_API.replace(/{playerTag}/, encodeURIComponent(playerTag));
  return await axios.get(uri)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      throw error;
                    });
}

async function fetchPlayerLegendRecord(playerTag) {
  const uri = `${URI_PLAYERS_LEGEND_RECORD.replace(/{playerTag}/, encodeURIComponent(playerTag))}`;

  return await axios.get(uri)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchAllLegendRecordPlayers() {
  return await axios.get(URI_PLAYERS_TAGS_LEGEND_RECORD)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchAllPlayerTags() {
  return await axios.get(URI_PLAYERS_ALL_TAGS)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchAllPlayersSummary() {
  // 전체 클랜원 요약 정보 조회
  const uri = `${URI_PLAYERS_ALL}?viewMode=summary`;

  return await axios.get(uri)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchPlayersSummaryByName(playerName) {
  const uri = URI_PLAYERS_ALL + `?viewMode=summary&name=${encodeURIComponent(playerName)}`
  return await axios.get(uri)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchAllClanPlayers() {
  return await axios.get(URI_PLAYERS_ALL)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function registerMember(playerTag) {
  const uri = `${URI_PLAYERS_DETAIL.replace(/{playerTag}/, encodeURIComponent(playerTag))}`;

  return await axios.post(uri)
                    .then((response) => {
                      alert('등록 되었습니다.');

                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);

                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);
                      return undefined;
                    });
}
async function deleteMember(playerTag) {
  const uri = `${URI_PLAYERS_DETAIL.replace(/{playerTag}/, encodeURIComponent(playerTag))}`;

  return await axios.delete(uri)
                    .then((response) => {
                      alert('삭제 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);
                      return false;
                    });
}

async function fetchSupportPlayers() {
  // 지원계정 목록 조회
  const uri = `${URI_PLAYERS_ALL}?accountType=support&viewMode=summary`;

  return await axios.get(uri)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function updatePlayerSupportYn(playerTag, supportYn) {
  const requestBody = {
    support_yn: supportYn
  }

  const uri = `${URI_PLAYERS_SUPPORT.replace(/{playerTag}/, encodeURIComponent(playerTag))}`
  return axios.put(uri, requestBody)
              .then(response => {
                const { data } = response
                let message = `지원계정 ${supportYn === 'Y' ? '등록' : '해제'} 되었습니다.`
                alert(message);
                return true;
              })
              .catch((error) => {
                console.error(error);
                return false;
              });
}

async function insertSupportPlayerBulk(playerTags) {
  const requestBody = {
    player_tags: playerTags
  }

  return axios.post(URI_PLAYERS_SUPPORT_BULK, requestBody)
              .then(response => {
                const { data } = response
                let message = `지원계정 일괄 등록 되었습니다.`
                alert(message);
                return true;
              })
              .catch((error) => {
                console.error(error);
                return false;
              });
}

async function fetchRankingHeroEquipments(clanTag) {
  const uri = URI_PLAYERS_RANKING_HERO_EQUIPMENTS + `?clanTag=${encodeURIComponent(clanTag)}`;
  return await axios.get(uri)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingPlayerTrophies() {
  return await axios.get(URI_PLAYERS_RANKING_CURRENT_TROPHIES)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingPlayerAttackWins() {
  return await axios.get(URI_PLAYERS_RANKING_ATTACK_WINS)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingPlayerDonations() {
  return await axios.get(URI_PLAYERS_RANKING_DONATIONS)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function fetchRankingPlayerDonationsReceived() {
  return await axios.get(URI_PLAYERS_RANKING_DONATIONS_RECEIVED)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}