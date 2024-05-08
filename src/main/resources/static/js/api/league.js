async function fetchLeagues() {
  return await axios.get("/leagues")
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

                      const { response } = error;
                      const { status } = response
                      // 서버 이용 불가로 메인 화면 이동
                      if (status >= 500) {
                        location.href = "/";
                      }
                    });
}