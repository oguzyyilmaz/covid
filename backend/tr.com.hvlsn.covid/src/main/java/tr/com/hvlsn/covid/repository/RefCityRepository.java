package tr.com.hvlsn.covid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.hvlsn.covid.domain.RefCity;

/**
 * Created by ogyilmaz on 4/23/2021 4:20 PM
 */
public interface RefCityRepository extends MongoRepository<RefCity, String> {
}
