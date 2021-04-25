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
import tr.com.hvlsn.covid.domain.RefCity;
import tr.com.hvlsn.covid.repository.CovidNewsRepository;
import tr.com.hvlsn.covid.repository.RefCityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class CityServiceTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

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
    private RefCityRepository cityRepository;

    @Test
    public void test_unit_getCityList_001() {
        List<RefCity> cityList = new ArrayList<>();
        cityList.add(new RefCity("06", "ANKARA"));
        when(cityRepository.findAll()).thenReturn(cityList);
        try {
            this.mockMvc.perform(get("/getCityList"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("test_unit_getCityList_001"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
