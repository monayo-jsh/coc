const PREFIX_FILE_API = '/api/files' // 파일 API

const URI_FILE_UPLOAD = `${PREFIX_FILE_API}/upload` // 파일 업로드
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

async function uploadFile(fileInfo, file) {
  const formData = new FormData();
  formData.append(
      "file_info",
      new Blob([JSON.stringify(fileInfo)], { type: "application/json" })
  );
  formData.append("file", file);
  return await axios.post(URI_FILE_UPLOAD, formData, {
                      headers: {
                        "Content-Type": "multipart/form-data"
                      }
                    })
                    .then((response) => {
                      alert("업로드가 완료되었습니다.");
                      const { data } = response;
                      return data;
                    })
                    .catch((error) => {
                      alert("업로드 실패, 문의 부탁드립니다.");
                      console.warn(error);
                      return undefined
                    })
}