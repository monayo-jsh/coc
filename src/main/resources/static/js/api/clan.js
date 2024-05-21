const URI_CLANS = '/clans'; //전체 클랜 목록 조회
const URI_CLANS_ONE = '/clans/{clanTag}'; //클랜 조회,생성

const URI_CLAN_DETAIL = '/clans/detail'; //클랜 상세 조회
const URI_CLAN_MEMBERS = '/clans/members' //클랜 멤버 조회
const URI_LATEST_CLAN_ASSIGNED_MEMBERS = `/clans/latest/assigned/members` //최신 클랜 배정 멤버 목록 조회
const URI_CLAN_ASSIGNED_MEMBERS = `/clans/{clanTag}/assigned/members` //클랜 배정 멤버 조회
const URI_CLAN_ASSIGNED_MEMBER = `/clans/{clanTag}/assigned/{seasonDate}/{playerTag}` //클랜 배정 멤버 삭제

const URI_CLAN_CONTENT = '/clans/content' //클랜 컨텐츠 업데이트

function deviceArray(array, size) {
  if (array.length === 1) return [array];

  const result = [];
  for (let i = 0; i < array.length; i += size) {
    result.push(array.slice(i, i + size));
  }
  return result;
}

async function registerClan(requestBody) {
  const uri = URI_CLANS_ONE.replace(/{clanTag}/, encodeURIComponent(requestBody.tag));

  return await axios.post(uri, requestBody)
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

async function findClan(clanTag) {
  const uri = URI_CLANS_ONE.replace(/{clanTag}/, encodeURIComponent(clanTag));

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
  const uri = `${URI_CLANS}/${encodeURIComponent(clanTag)}`
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

async function updateClanContent(requestBody) {
  return await axios.put(URI_CLAN_CONTENT, requestBody)
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
  return axios.get(URI_CLANS)
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

function makeClanDetailRequest(clans) {
  const uri = `${URI_CLAN_DETAIL}?clanTags=${encodeURIComponent(clans.map(clan => clan.tag))}`;
  return axios.get(uri);
}

async function fetchClansFromExternal(clans) {
  const requests = deviceArray(clans, clans.length/2).map(makeClanDetailRequest);

  let results = [];
  // 클랜 상세 조회
  return axios.all(requests)
              .then((responses) => {
                responses.forEach((response) => {

                  const { data } = response;
                  data.forEach((response) => {
                    results = results.concat(response);
                  })
                });

                return results;
              })
              .catch((error) => {
                console.error(error);
                return clans;
              });
}

function makeClanMemberRequest(clans) {
  const uri = `${URI_CLAN_MEMBERS}?clanTags=${encodeURIComponent(clans.map(clan => clan.tag))}`;
  return axios.get(uri);
}

async function fetchClanMembers(clans) {
  const requests = deviceArray(clans, clans.length/2).map(makeClanMemberRequest);

  let allClanMembers = [];
  await axios.all(requests)
             .then((responses) => {
               responses.forEach((response) => {

                 const { data } = response;
                 data.forEach((response) => {
                   const { clanTag, members } = response
                   if (members) {
                     // concat member
                     allClanMembers = allClanMembers.concat(members);
                   }
                 })

               });
             })
             .catch((error) => {
               console.error(error);
               const { response } = error;
               const { status } = response
               // 서버 이용 불가로 메인 화면 이동
               if (status >= 500) {
                 location.href = "/";
               }
             })

  return allClanMembers;
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
                      alert('배정 되었습니다.');
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
                      alert('삭제 되었습니다.');
                      return true;
                    })
                    .catch((error) => {
                      console.error(error);
                      return false;
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
                      return [];
                    });
}