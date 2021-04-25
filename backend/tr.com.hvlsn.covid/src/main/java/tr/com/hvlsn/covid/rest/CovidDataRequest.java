package tr.com.hvlsn.covid.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by ogyilmaz on 4/22/2021 11:53 PM
 */
@Data
public class CovidDataRequest {
    private LocalDateTime reportDate;
    @JsonProperty
    private boolean isCumulative;
    private String cityCode;
}
