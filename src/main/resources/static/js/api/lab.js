const LAB_LINK_ALL = "/lab/link/all"
async function fetchLabLinkAll() {
  return await axios.get(LAB_LINK_ALL)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}