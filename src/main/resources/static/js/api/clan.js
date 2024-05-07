const URI_CLANS = '/clans'; //전체 클랜 목록 조회
const URI_CLAN_MEMBERS = '/clans/members' //클랜 멤버 조회

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