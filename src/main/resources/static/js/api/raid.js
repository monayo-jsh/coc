const URI_RAID_SCORE = '/raid/score/{playerTag}'; //멤버 습격전 기록 조회

async function fetchPlayerRaidScore(playerTag) {
  const uri = URI_RAID_SCORE.replace(/{playerTag}/, encodeURIComponent(playerTag));

  return await axios.get(uri)
                    .then((response) => {
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}