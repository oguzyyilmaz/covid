package tr.com.hvlsn.covid.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Created by ogyilmaz on 4/22/2021 11:55 PM
 */
@Data
@Document
public class CovidNews {
    @Id
    private String id;
    private String city;
    private LocalDate date;
    private Integer covidCase;
    private Integer covidDeath;
    private Integer covidDischarge;
    private String news;

}
