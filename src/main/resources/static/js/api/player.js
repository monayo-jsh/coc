const URI_PLAYERS = '/players' //멤버 상세 조회

function deviceArray(array, size) {
  const result = [];
  for (let i = 0; i < array.length; i += size) {
    result.push(array.slice(i, i + size));
  }
  return result;
}

function overridePlayer(memberMap, member) {
  let fetchMember = memberMap[member.tag]
  if (fetchMember) {
    fetchMember = Object.assign(fetchMember, member)

    fetchMember.heroLevelSum = calcHeroLevelSum(fetchMember.heroes);

    Object.assign(member, fetchMember); //member override
  }
}

function makePlayerRequest(players) {
  const uri = `${URI_PLAYERS}?playerTags=${encodeURIComponent(players.map(player => player.tag))}`
  return axios.get(uri);
}

async function fetchPlayerDetail(members) {
  const requests = deviceArray(members, members.length/3).map(makePlayerRequest);
  return await axios.all(requests)
                    .then((responses) => {
                      responses.forEach((response) => {
                        const { data } = response
                        if (!data) {
                          console.error('response data empty..', response);
                          return;
                        }

                        const memberMap = makeMemberMapByTag(data);

                        for (let member of members) {
                          overridePlayer(memberMap, member);
                        }
                      })
                    })
                    .catch((error) => {
                      console.error(error);
                      const { response } = error;
                      const { status } = response
                      // 서버 이용 불가로 메인 화면 이동
                      if (status >= 500) {
                        location.href = "/";
                      }
                    });
}

function makeMemberMapByTag(clanMembers) {
  return clanMembers.reduce((memberMap, member) => {
    memberMap[member.tag] = member
    return memberMap
  }, {});
}