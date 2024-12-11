const PREFIX_LAB_API = '/api/lab' // 연구소 API

const URI_LAB_LINKS = `${PREFIX_LAB_API}/links` // 연구소 목록 조회

async function fetchLabLinkAll() {
  return await axios.get(URI_LAB_LINKS)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}