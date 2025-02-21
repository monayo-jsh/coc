const PREFIX_FILE_API = '/api/files' // 파일 API

const URI_FILE_DOWNLOAD = `${PREFIX_FILE_API}/download/{downloadType}/{fileName}` // 파일 다운로드

async function fetchFile(downloadType, fileName) {
  const url = URI_FILE_DOWNLOAD.replace(/{downloadType}/, downloadType).replace(/{fileName}/, fileName)
  return await axios.get(url, {
                      responseType: "blob"
                    })
                    .then((response) => {
                      const { data } = response
                      return data
                    })
                    .catch((error) => {
                      console.warn(error);
                      return undefined
                    })
}