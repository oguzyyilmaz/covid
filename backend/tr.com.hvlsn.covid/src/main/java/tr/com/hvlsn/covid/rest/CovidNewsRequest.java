package tr.com.hvlsn.covid.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ogyilmaz on 4/22/2021 11:53 PM
 */
@Data
public class CovidNewsRequest implements Serializable {
    private String covidNews;
}
