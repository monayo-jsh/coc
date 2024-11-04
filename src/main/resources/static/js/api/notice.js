const PREFIX_NOTICE_API = '/api/notices' // 공지사항 API

const URI_NOTICES = `${PREFIX_NOTICE_API}`; // 전체 공지사항 목록
const URI_POSTING_NOTICES = `${PREFIX_NOTICE_API}/posting` // 게시중인 공지사항 목록 조회

async function fetchPostingNotices() {
  return await axios.get(URI_POSTING_NOTICES)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return {};
                    });
}

async function fetchNotices() {
  return await axios.get(URI_NOTICES)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return {};
                    });
}