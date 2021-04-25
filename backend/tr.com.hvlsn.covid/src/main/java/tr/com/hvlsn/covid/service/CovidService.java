package tr.com.hvlsn.covid.service;

import tr.com.hvlsn.covid.rest.CovidDataRequest;
import tr.com.hvlsn.covid.rest.CovidDataResponse;
import tr.com.hvlsn.covid.rest.CovidNewsRequest;

/**
 * Created by ogyilmaz on 4/22/2021 11:48 PM
 */

public interface CovidService {
    void saveCovidNews(CovidNewsRequest covidNewsRequest);

    CovidDataResponse getCovidData(CovidDataRequest request);
}
