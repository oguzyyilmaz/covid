package tr.com.hvlsn.covid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tr.com.hvlsn.covid.domain.CovidNews;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ogyilmaz on 4/23/2021 1:06 PM
 */

public interface CovidNewsRepository extends MongoRepository<CovidNews, String> {

    @Query("{'date': {" +
            "$gte: ?0, $lte:?1 " +
            "}" +
            "}")
    List<CovidNews> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("{$and: [{'date': {$gte: ?0, $lte:?1 } }, {'city': ?2} ]}")
    List<CovidNews> findByDateBetweenAndCity(LocalDate startDate, LocalDate endDate, String city);

    List<CovidNews> findByDate(LocalDate endDate);

    List<CovidNews> findByDateAndCity(LocalDate endDate, String city);
}
