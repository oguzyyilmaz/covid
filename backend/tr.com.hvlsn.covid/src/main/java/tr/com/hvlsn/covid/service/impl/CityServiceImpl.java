package tr.com.hvlsn.covid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.hvlsn.covid.domain.RefCity;
import tr.com.hvlsn.covid.repository.RefCityRepository;
import tr.com.hvlsn.covid.rest.CityResponse;
import tr.com.hvlsn.covid.service.CityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ogyilmaz on 4/25/2021 4:56 AM
 */
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final RefCityRepository cityRepository;

    @Override
    public List<CityResponse> getCityList() {
        List<RefCity> refCityList = cityRepository.findAll();
        List<CityResponse> responseList = new ArrayList<>();

        for (RefCity city : refCityList) {
            CityResponse response = new CityResponse();
            response.setValue(city.getCityCode());
            response.setLabel(city.getCityName());
            responseList.add(response);
        }

        return responseList;
    }
}
