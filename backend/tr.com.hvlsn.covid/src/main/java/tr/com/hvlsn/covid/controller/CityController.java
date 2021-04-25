package tr.com.hvlsn.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.hvlsn.covid.rest.CityResponse;
import tr.com.hvlsn.covid.service.CityService;

import java.util.List;

/**
 * Created by ogyilmaz on 4/25/2021 4:53 AM
 */

@RestController
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping(value = "/getCityList")
    public List<CityResponse> getCityList() {
        return cityService.getCityList();
    }


}
