package tr.com.hvlsn.covid;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tr.com.hvlsn.covid.domain.CovidNews;
import tr.com.hvlsn.covid.domain.RefCity;
import tr.com.hvlsn.covid.repository.CovidNewsRepository;
import tr.com.hvlsn.covid.repository.RefCityRepository;
import tr.com.hvlsn.covid.rest.CovidDataRequest;
import tr.com.hvlsn.covid.rest.CovidNewsRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ogyilmaz on 4/25/2021 8:29 AM
 */
@Slf4j
@RunWith(SpringRunner.class)
@EnableMongoRepositories(basePackageClasses = {RefCityRepository.class, CovidNewsRepository.class})
@EnableAutoConfiguration
@SpringBootTest(classes = CovidApplication.class)
public class CovidServiceTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .defaultRequest(get("/").
                        accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @MockBean
    private CovidNewsRepository covidNewsRepository;

    @MockBean
    private RefCityRepository cityRepository;

    @Test
    public void test_unit_save_covid_news_001() {
        CovidNewsRequest request = new CovidNewsRequest();
        request.setCovidNews("20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.");
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("06", "ANKARA"));
        when(cityRepository.findAll()).thenReturn(cityList);
        try {
            this.mockMvc.perform(put("/saveCovidNews").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_save_covid_news_001"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_save_covid_news_002() {
        CovidNewsRequest request = new CovidNewsRequest();
        request.setCovidNews("Korona virüs salgınında yapılan testlerde 19.04.2020 tarihinde  İstanbul da 30 yeni vaka tespit edil. İstanbul da taburcu sayısı 7 kişi .  3 kişi de vefat etti. ");
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        try {
            this.mockMvc.perform(put("/saveCovidNews").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_save_covid_news_002"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_save_covid_news_003() {
        CovidNewsRequest request = new CovidNewsRequest();
        request.setCovidNews("19.04.2020 tarihinde İstanbul  için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu.  3 kişin de vefat ettiği öğrenildi.  ");
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        try {
            this.mockMvc.perform(put("/saveCovidNews").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_save_covid_news_003"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_save_covid_news_wrong_city_name_004() {
        CovidNewsRequest request = new CovidNewsRequest();
        request.setCovidNews("19.04.2020 tarihinde İçel için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu.  3 kişin de vefat ettiği öğrenildi.  ");
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("33", "MERSİN"));
        when(cityRepository.findAll()).thenReturn(cityList);
        try {
            this.mockMvc.perform(put("/saveCovidNews").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_save_covid_news_wrong_city_name_004"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_save_covid_news_wrong_covid_number_005() {
        CovidNewsRequest request = new CovidNewsRequest();
        request.setCovidNews("19.04.2020 tarihinde istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde xxx yeni vaka tespit edildi. taburcu sayısı ise 7 oldu.  3 kişin de vefat ettiği öğrenildi.  ");
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        try {
            this.mockMvc.perform(put("/saveCovidNews").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_save_covid_news_wrong_covid_number_005"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_get_covid_data_cumulative_true_all_city_006() {
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        List<CovidNews> covidNewsList = new ArrayList<>();
        CovidNews covidNews = new CovidNews();
        covidNews.setNews("19.04.2020 tarihinde istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi. taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.  ");
        covidNews.setCovidCase(1);
        covidNews.setCovidDeath(1);
        covidNews.setCovidDischarge(1);
        covidNews.setDate(LocalDate.now());
        covidNews.setId(UUID.randomUUID().toString());
        covidNews.setCity("İSTANBUL");
        covidNewsList.add(covidNews);
        when(covidNewsRepository.findByDateBetween(any(), any())).thenReturn(covidNewsList);

        CovidDataRequest request = new CovidDataRequest();
        request.setCumulative(true);
        request.setCityCode("-1");
        request.setReportDate(LocalDateTime.now());
        try {
            this.mockMvc.perform(post("/getCovidData").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_get_covid_data_cumulative_true_all_city_006"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_get_covid_data_cumulative_true_selected_city_007() {
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        List<CovidNews> covidNewsList = new ArrayList<>();
        CovidNews covidNews = new CovidNews();
        covidNews.setNews("19.04.2020 tarihinde istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi. taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.  ");
        covidNews.setCovidCase(1);
        covidNews.setCovidDeath(1);
        covidNews.setCovidDischarge(1);
        covidNews.setDate(LocalDate.now());
        covidNews.setId(UUID.randomUUID().toString());
        covidNews.setCity("İSTANBUL");
        covidNewsList.add(covidNews);
        when(covidNewsRepository.findByDateBetweenAndCity(any(), any(), any())).thenReturn(covidNewsList);

        CovidDataRequest request = new CovidDataRequest();
        request.setCumulative(true);
        request.setCityCode("34");
        request.setReportDate(LocalDateTime.now());
        try {
            this.mockMvc.perform(post("/getCovidData").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_get_covid_data_cumulative_true_selected_city_007"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_get_covid_data_cumulative_false_all_city_008() {
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        List<CovidNews> covidNewsList = new ArrayList<>();
        CovidNews covidNews = new CovidNews();
        covidNews.setNews("19.04.2020 tarihinde istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi. taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.  ");
        covidNews.setCovidCase(1);
        covidNews.setCovidDeath(1);
        covidNews.setCovidDischarge(1);
        covidNews.setDate(LocalDate.now());
        covidNews.setId(UUID.randomUUID().toString());
        covidNews.setCity("İSTANBUL");
        covidNewsList.add(covidNews);
        when(covidNewsRepository.findByDate(any())).thenReturn(covidNewsList);

        CovidDataRequest request = new CovidDataRequest();
        request.setCumulative(false);
        request.setCityCode("-1");
        request.setReportDate(LocalDateTime.now());
        try {
            this.mockMvc.perform(post("/getCovidData").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_get_covid_data_cumulative_false_all_city_008"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_unit_get_covid_data_cumulative_false_selected_city_009() {
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("34", "İSTANBUL"));
        when(cityRepository.findAll()).thenReturn(cityList);
        List<CovidNews> covidNewsList = new ArrayList<>();
        CovidNews covidNews = new CovidNews();
        covidNews.setNews("19.04.2020 tarihinde istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi. taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.  ");
        covidNews.setCovidCase(1);
        covidNews.setCovidDeath(1);
        covidNews.setCovidDischarge(1);
        covidNews.setDate(LocalDate.now());
        covidNews.setId(UUID.randomUUID().toString());
        covidNews.setCity("İSTANBUL");
        covidNewsList.add(covidNews);
        when(covidNewsRepository.findByDateAndCity(any(), any())).thenReturn(covidNewsList);

        CovidDataRequest request = new CovidDataRequest();
        request.setCumulative(false);
        request.setCityCode("34");
        request.setReportDate(LocalDateTime.now());
        try {
            this.mockMvc.perform(post("/getCovidData").content(this.objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_get_covid_data_cumulative_false_selected_city_009"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
