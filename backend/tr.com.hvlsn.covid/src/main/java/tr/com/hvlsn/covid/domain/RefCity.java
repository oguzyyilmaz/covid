package tr.com.hvlsn.covid.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by ogyilmaz on 4/23/2021 4:21 PM
 */
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class RefCity {
    @Id
    private String cityCode;
    private String cityName;
}
