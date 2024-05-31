const URI_PLAYERS = '/players'; //멤버 상세 조회
const URI_PLAYERS_ALL = '/players/all'; //전체 클랜원 조회
const URI_PLAYERS_ALL_TAGS = '/players/all/tags'; //전체 클랜원 태그 조회
const URI_PLAYERS_SUPPORT_ALL = '/players/support/all'; //지원 계정 목록 조회
const URI_PLAYERS_REALTIME = '/players/{playerTag}'; //멤버 상세 조회 (항시 실연동)
const URI_PLAYERS_DETAIL = '/players/{playerTag}'; //멤버 등록,삭제

async function findPlayer(playerTag) {
  const uri = URI_PLAYERS_REALTIME.replace(/{playerTag}/, encodeURIComponent(playerTag));
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
  return await axios.get(URI_PLAYERS_SUPPORT_ALL)
                    .then(response => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}