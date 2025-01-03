const PREFIX_CLAN_API = '/api/clans'; // 클랜 API

const URL_CLAN_CURRENT_WAR_LEAGUE = "/clans/{clanTag}/current/war/league"; //클랜 진행중인 리그전 조회
const URL_CLAN_CURRENT_WAR_LEAGUE_ROUND = "/clans/war/league/{warTag}"; //클랜 리그전 전쟁 정보 조회

const URI_CLAN = `${PREFIX_CLAN_API}`; //클랜 목록 조회
const URI_CLAN_REGISTER = `${PREFIX_CLAN_API}/{clanTag}`; //클랜 등록
const URI_CLAN_DELETE = `${PREFIX_CLAN_API}/{clanTag}`; //클랜 삭제

const URI_CLAN_CONTENT_ACTIVATION = `${PREFIX_CLAN_API}/{clanTag}/content` // 클랜 컨텐츠 활성화 수정

const URI_CLAN_EXTERNAL = `${PREFIX_CLAN_API}/{clanTag}/external`; //클랜 상세 조회 (OPEN API)
const URI_CLAN_MEMBERS_EXTERNAL = `${PREFIX_CLAN_API}/{clanTag}/members/external` //클랜 멤버 조회 (OPEN API)

const URI_LATEST_CLAN_ASSIGNED_DATE = `/api/clan/assign/latest-month` //최신 클랜 배정 날짜 조회
const URI_LATEST_CLAN_ASSIGNED_MEMBERS = `/clans/assigned/members/latest` //최신 클랜 배정 멤버 목록 조회
const URI_CLAN_ASSIGNED_MEMBERS = `/clans/{clanTag}/assigned/members` //클랜 배정 멤버 조회
const URI_CLAN_ASSIGNED_MEMBER = `/clans/{clanTag}/assigned/{seasonDate}/{playerTag}` //클랜 배정 멤버 삭제
const URI_CLAN_ASSIGNED_MEMBER_BULK = `/clans/assigned/members` //클랜 일괄 배정

const URI_LATEST_LEAGUE_ASSIGNED_DATE = `/clans/league/assigned/latest` //최신 리그 배정 날짜 조회
const URI_LATEST_LEAGUE_ASSIGNED_MEMBERS = `/clans/league/assigned/members/latest` //최신 리그 배정 멤버 목록 조회
const URI_CLAN_LEAGUE_ASSIGNED_MEMBERS = `/clans/{clanTag}/league/assigned/members` //리그 배정 멤버 조회
const URI_CLAN_LEAGUE_ASSIGNED_MEMBER = `/clans/{clanTag}/league/assigned/{seasonDate}/{playerTag}` //리그 배정 멤버 삭제
const URI_CLAN_LEAGUE_ASSIGNED_MEMBER_BULK = `/clans/league/assigned/members` //리그 일괄 배정

const URL_CLAN_GAME_LATEST = `/api/clan/game/latest`; // 최근 클랜 게임 목록 조회

function divideClanArray(array, size) {
  if (array.length === 1) return [array];

  const result = [];
  for (let i = 0; i < array.length; i += size) {
    result.push(array.slice(i, i + size));
  }
  return result;
}

async function registerClan(clanTag) {
  const uri = URI_CLAN_REGISTER.replace(/{clanTag}/, encodeURIComponent(clanTag));

  return await axios.post(uri)
                    .then((response) => {
                      alert('등록 되었습니다.');

                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);

                      console.error(error);
                      return null;
                    });
}

function makeClanDetailURI(clanTag) {
  return URI_CLAN_EXTERNAL.replace(/{clanTag}/, encodeURIComponent(clanTag));
}

async function fetchClanDetailFromExternal(clanTag) {
  const uri = makeClanDetailURI(clanTag)

  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return null;
                    })
}

async function deleteClan(clanTag) {
  const uri = URI_CLAN_DELETE.replace(/{clanTag}/, encodeURIComponent(clanTag));

  return await axios.delete(uri)
                    .then((response) => {
                      alert('삭제 되었습니다.');
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
                    });
}

async function updateClanContent(clanTag, requestBody) {
  const uri = URI_CLAN_CONTENT_ACTIVATION.replace(/{clanTag}/, encodeURIComponent(clanTag))

  return await axios.put(uri, requestBody)
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

async function fetchClans() {
  // 전체 클랜 조회
  return axios.get(URI_CLAN)
              .then((response) => {
                const { data } = response
                if (!data) {
                  return [];
                }

                return data;
              })
              .catch((error) => {
                console.error(error);
                return [];
              });
}

function makeClanDetailRequest(clan) {
  const uri = makeClanDetailURI(clan.tag);
  return axios.get(uri);
}

async function fetchClanDetailsFromExternal(clans) {
  const requests = clans.map(makeClanDetailRequest);

  let results = [];

  // 클랜 상세 조회
  await axios.all(requests)
             .then((responses) => {
               responses.forEach((response) => {
                 const { data } = response;
                 results.push(data);
               });
             })
             .catch((error) => {
               console.error(error);
             });

  return results
}

async function fetchClanMembers(clanTag) {
  const uri = URI_CLAN_MEMBERS_EXTERNAL.replace(/{clanTag}/, encodeURIComponent(clanTag));

  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    })
}

async function fetchClanAssignedMembers(clanTag) {
  const URI = URI_CLAN_ASSIGNED_MEMBERS.replace(/{clanTag}/, encodeURIComponent(clanTag));
  return await axios.get(URI)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function assignedClanPlayer(clanTag, seasonDate, playerTag) {
  const uri = `${URI_CLAN_ASSIGNED_MEMBER.replace(/{clanTag}/, encodeURIComponent(clanTag))
                                         .replace(/{seasonDate}/, seasonDate)
                                         .replace(/{playerTag}/, encodeURIComponent(playerTag))}`

  return await axios.post(uri)
                    .then((response) => {
                      alert('클랜 배정 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);

                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);
                      return false;
                    });
}

async function registerClanAssignedPlayers(seasonDate, players) {
  const requestBody = {
    season_date: seasonDate,
    players: players
  }

  return await axios.post(URI_CLAN_ASSIGNED_MEMBER_BULK, requestBody)
                    .then((response) => {
                      alert('클랜 배정 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);

                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);
                      return false;
                    });
}

async function deleteAssignedMember(clanTag, seasonDate, playerTag) {
  const uri = `${URI_CLAN_ASSIGNED_MEMBER.replace(/{clanTag}/, encodeURIComponent(clanTag))
                                         .replace(/{seasonDate}/, seasonDate)
                                         .replace(/{playerTag}/, encodeURIComponent(playerTag))}`

  return await axios.delete(uri)
                    .then((response) => {
                      alert('배정 제외 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);
                      return false;
                    });
}

async function latestClanAssignedDate() {
  return await axios.get(URI_LATEST_CLAN_ASSIGNED_DATE)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return null;
                    });
}

async function latestClanAssignedMembers() {
  return await axios.get(URI_LATEST_CLAN_ASSIGNED_MEMBERS)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return {};
                    });
}

async function fetchClanLeagueAssignedMembers(clanTag) {
  const URI = URI_CLAN_LEAGUE_ASSIGNED_MEMBERS.replace(/{clanTag}/, encodeURIComponent(clanTag));
  return await axios.get(URI)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function latestLeagueAssignedDate() {
  return await axios.get(URI_LATEST_LEAGUE_ASSIGNED_DATE)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return null;
                    });
}

async function latestLeagueAssignedMembers() {
  return await axios.get(URI_LATEST_LEAGUE_ASSIGNED_MEMBERS)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function assignedLeaguePlayer(clanTag, seasonDate, playerTag) {
  const uri = `${URI_CLAN_LEAGUE_ASSIGNED_MEMBER.replace(/{clanTag}/, encodeURIComponent(clanTag))
                                                .replace(/{seasonDate}/, seasonDate)
                                                .replace(/{playerTag}/, encodeURIComponent(playerTag))}`

  return await axios.post(uri)
                    .then((response) => {
                      alert('리그 배정 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);

                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);
                      return false;
                    });
}

async function deleteClanLeagueAssignedMember(clanTag, seasonDate, playerTag) {
  const uri = `${URI_CLAN_LEAGUE_ASSIGNED_MEMBER.replace(/{clanTag}/, encodeURIComponent(clanTag))
                                                .replace(/{seasonDate}/, seasonDate)
                                                .replace(/{playerTag}/, encodeURIComponent(playerTag))}`

  return await axios.delete(uri)
                    .then((response) => {
                      alert('리그 배정 제외 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);
                      return false;
                    });
}

async function registerClanLeagueAssignedPlayers(seasonDate, players) {
  const requestBody = {
    season_date: seasonDate,
    players: players
  }

  return await axios.post(URI_CLAN_LEAGUE_ASSIGNED_MEMBER_BULK, requestBody)
                    .then((response) => {
                      alert('리그 배정 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);

                      let message = error.message;
                      const { response } = error;
                      if (response && response.data) {
                        message = response.data;
                      }

                      alert(message);
                      return false;
                    });
}

async function fetchWarClans(warType = "none") {
  const option = {
    params: {
      type: warType
    }
  }

  return await axios.get(URI_CLAN, option)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error)
                      return [];
                    });
}

async function fetchCompetitionClans() {
  const option = {
    params: {
      type: 'competition'
    }
  }

  return await axios.get(URI_CLAN, option)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error)
                      return [];
                    });
}

async function fetchCapitalClans() {
  const option = {
    params: {
      type: 'capital'
    }
  }

  return await axios.get(URI_CLAN, option)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error)
                      return [];
                    });
}

async function fetchClanWarLeague(clanTag) {
  const URI = URL_CLAN_CURRENT_WAR_LEAGUE.replace(/{clanTag}/, encodeURIComponent(clanTag));

  return axios.get(URI)
              .then((response) => {
                const { data } = response
                return data;
              })
              .catch((error) => {
                console.error(error)
                return {};
              });
}

function makeCurrentWarLeagueRoundRequest(warTag, clanTag, season) {
  let uri = URL_CLAN_CURRENT_WAR_LEAGUE_ROUND.replace(/{warTag}/, encodeURIComponent(warTag));
  uri += `?clanTag=${encodeURIComponent(clanTag)}&season=${season}`;
  return axios.get(uri);
}
async function fetchCurrentWarLeagueRound(warTags, clanTag, season) {
  const requests = divideClanArray(warTags, 1).map(warTag => makeCurrentWarLeagueRoundRequest(warTag, clanTag, season))

  let results = [];
  return axios.all(requests)
              .then((responses) => {

                responses.forEach((response) => {
                  const { data } = response;
                  results = results.concat(data);
                })

                return results;
              })
              .catch((error) => {
                console.error(error)
                return [];
              });
}


async function fetchLatestClanGame() {
  // 전체 클랜 조회
  return axios.get(URL_CLAN_GAME_LATEST)
              .then((response) => {
                const { data } = response
                return data;
              })
              .catch((error) => {
                console.error(error);
                return {};
              });
}