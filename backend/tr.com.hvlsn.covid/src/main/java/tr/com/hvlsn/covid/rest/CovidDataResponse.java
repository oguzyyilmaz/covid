package tr.com.hvlsn.covid.rest;

import lombok.Data;

/**
 * Created by ogyilmaz on 4/22/2021 11:53 PM
 */
@Data
public class CovidDataResponse {
    private Integer totalCovidCase;
    private Integer totalCovidDeath;
    private Integer totalCovidDischarge;
}
