import http from './http-common';

class CovidDataService {
    create(data) {
        return http.put('/saveCovidNews',data);
    }
    getDailyCovidData(data) {
        return http.post('/getCovidData',data);
    }
    getCityList() {
        return http.get('/getCityList');
    }
}
export default new CovidDataService();
