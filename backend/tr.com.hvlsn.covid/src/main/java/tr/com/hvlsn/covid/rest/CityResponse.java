package tr.com.hvlsn.covid.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ogyilmaz on 4/25/2021 4:55 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {
    private String value;
    private String label;
}
