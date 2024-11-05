const PREFIX_NOTICE_API = '/api/notices' // 공지사항 API

const URI_NOTICES = `${PREFIX_NOTICE_API}`; // 전체 공지사항 목록
const URI_POSTING_NOTICES = `${PREFIX_NOTICE_API}/posting` // 게시중인 공지사항 목록 조회
const URI_NOTICES_VISIBLE = `${PREFIX_NOTICE_API}/{noticeId}/visible` // 공지사항 노출 설정 여부 수정

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

async function createNotice(requestBody) {
  return await axios.post(URI_NOTICES, requestBody)
                    .then((response) => {
                      alert('등록 되었습니다.');
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

async function updateNoticeVisible(noticeId) {
  const uri = URI_NOTICES_VISIBLE.replace(/{noticeId}/, noticeId)

  return await axios.put(uri)
                    .then((response) => {
                      alert('적용 되었습니다.');
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