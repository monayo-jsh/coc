async function fetchLeagues() {
  return await axios.get("/leagues")
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return {};
                    });
}