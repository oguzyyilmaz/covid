package tr.com.hvlsn.covid.service;

import tr.com.hvlsn.covid.rest.CityResponse;

import java.util.List;

/**
 * Created by ogyilmaz on 4/25/2021 4:55 AM
 */
public interface CityService {

    List<CityResponse> getCityList();
}
