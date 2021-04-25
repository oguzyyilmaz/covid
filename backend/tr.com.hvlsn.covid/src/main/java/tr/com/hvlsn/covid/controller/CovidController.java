package tr.com.hvlsn.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.hvlsn.covid.rest.CovidDataRequest;
import tr.com.hvlsn.covid.rest.CovidDataResponse;
import tr.com.hvlsn.covid.rest.CovidNewsRequest;
import tr.com.hvlsn.covid.service.CovidService;

/**
 * Created by ogyilmaz on 4/22/2021 11:48 PM
 */
@RestController
@RequiredArgsConstructor
public class CovidController {
    private final CovidService covidService;

    @PutMapping(value = "/saveCovidNews")
    public void saveCovidNews (@RequestBody CovidNewsRequest request) {
        covidService.saveCovidNews(request);
    }

    @PostMapping(value = "/getCovidData")
    public CovidDataResponse getCovidWithNumbers (@RequestBody CovidDataRequest request) {
        return covidService.getCovidData(request);
    }
}
