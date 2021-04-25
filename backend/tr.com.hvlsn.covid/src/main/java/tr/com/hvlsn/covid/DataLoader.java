package tr.com.hvlsn.covid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tr.com.hvlsn.covid.domain.RefCity;
import tr.com.hvlsn.covid.repository.CovidNewsRepository;
import tr.com.hvlsn.covid.repository.RefCityRepository;

/**
 * Created by ogyilmaz on 4/23/2021 4:19 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RefCityRepository cityRepository;
    private final CovidNewsRepository covidNewsRepository;
    private static final String[] CITIES = {"ADANA", "ADIYAMAN", "AFYON", "AĞRI", "AMASYA", "ANKARA",
            "ANTALYA", "ARTVİN", "AYDIN", "BALIKESİR", "BİLECİK", "BİNGÖL",
            "BİTLİS", "BOLU", "BURDUR", "BURSA", "ÇANAKKALE", "ÇANKIRI", "ÇORUM",
            "DENİZLİ", "DİYARBAKIR", "EDİRNE", "ELAZIĞ", "ERZİNCAN", "ERZURUM",
            "ESKİŞEHİR", "GAZİANTEP", "GİRESUN", "GÜMÜŞHANE", "HAKKARİ", "HATAY",
            "ISPARTA", "MERSİN", "İSTANBUL", "İZMİR", "KARS", "KASTAMONU",
            "KAYSERİ", "KIRKLARELİ", "KIRŞEHİR", "KOCAELİ", "KONYA", "KÜTAHYA",
            "MALATYA", "MANİSA", "KAHRAMANMARAŞ", "MARDİN", "MUĞLA", "MUŞ", "NEVŞEHİR",
            "NİĞDE", "ORDU", "RİZE", "SAKARYA", "SAMSUN", "SİİRT", "SİNOP", "SİVAS",
            "TEKİRDAĞ", "TOKAT", "TRABZON", "TUNCELİ", "ŞANLIURFA", "UŞAK", "VAN",
            "YOZGAT", "ZONGULDAK", "AKSARAY", "BAYBURT", "KARAMAN", "KIRIKKALE",
            "BATMAN", "ŞIRNAK", "BARTIN", "ARDAHAN", "IĞDIR", "YALOVA", "KARABÜK",
            "KİLİS", "OSMANİYE", "DÜZCE"};

    public void run(ApplicationArguments args) {
        if (!cityRepository.findAll().isEmpty())
            cityRepository.deleteAll();
        for (int i = 0; i < CITIES.length; i++) {
            var plateCode = i + 1;
            cityRepository.save(new RefCity(plateCode < 10 ? "0" + plateCode : String.valueOf(plateCode), CITIES[i]));
        }
        if (!covidNewsRepository.findAll().isEmpty())
            covidNewsRepository.deleteAll();
        log.info("Initial data defined");
    }
}