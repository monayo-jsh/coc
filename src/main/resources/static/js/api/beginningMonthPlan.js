const PREFIX_BEGINNING_MONTH_PLAN_API = '/api/beginning-month-plan'; // 월초 일정 API

const URI_BEGINNING_MONTHS = `${PREFIX_BEGINNING_MONTH_PLAN_API}`; // 월초 일정 목록 조회
const URI_BEGINNING_MONTH_LATEST = `${PREFIX_BEGINNING_MONTH_PLAN_API}/latest`; // 최근 월초 일정 조회

const URI_BEGINNING_MONTH_PLAN_CREATE = `${PREFIX_BEGINNING_MONTH_PLAN_API}`; // 월초 일정 등록

async function fetchBeginningMonthLatest() {
  return await axios.get(URI_BEGINNING_MONTH_LATEST)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return undefined;
                    });
}

async function fetchBeginningMonths() {
  return await axios.get(URI_BEGINNING_MONTHS)
                    .then((response) => {
                      const { data } = response
                      return data;
                    })
                    .catch((error) => {
                      console.error(error);
                      return [];
                    });
}

async function createBeginningMonthPlan(requestBody) {
  const jsonData = JSON.stringify(Object.fromEntries(requestBody));
  const options = {
    headers: {
      "Content-type": "application/json"
    }
  }
  return await axios.post(URI_BEGINNING_MONTH_PLAN_CREATE, jsonData, options)
                    .then((response) => {
                      const { data } = response
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

                      return false;
                    });
}