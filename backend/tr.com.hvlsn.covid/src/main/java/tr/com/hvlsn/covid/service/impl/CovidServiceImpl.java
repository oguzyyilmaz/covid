package tr.com.hvlsn.covid.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.com.hvlsn.covid.domain.CovidNews;
import tr.com.hvlsn.covid.domain.RefCity;
import tr.com.hvlsn.covid.repository.CovidNewsRepository;
import tr.com.hvlsn.covid.repository.RefCityRepository;
import tr.com.hvlsn.covid.rest.CovidDataRequest;
import tr.com.hvlsn.covid.rest.CovidDataResponse;
import tr.com.hvlsn.covid.rest.CovidNewsRequest;
import tr.com.hvlsn.covid.service.CovidService;
import tr.com.hvlsn.covid.util.CovidConst;
import tr.com.hvlsn.covid.util.DateConverter;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ogyilmaz on 4/22/2021 11:51 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CovidServiceImpl implements CovidService {
    private final CovidNewsRepository covidNewsRepository;
    private final RefCityRepository cityRepository;

    @Value("${hvlsn.covid.first.case.date:11.03.2020}")
    private String firstCovidCase; //Covid-19 ilk görüldüğü tarih

    @Override
    public void saveCovidNews(CovidNewsRequest request) {
        CovidNews covidNews = new CovidNews();
        covidNews.setId(UUID.randomUUID().toString());
        covidNews.setCity(findCity(request.getCovidNews()));
        String covidDate = findDate(request.getCovidNews());
        String dateExtractedExpression = request.getCovidNews().replace(covidDate, "");
        covidNews.setDate(DateConverter.toDefaultLocalDate(covidDate));
        covidNews.setCovidCase(findCovidNumbers(dateExtractedExpression, CovidConst.COVID_CASE_KEYWORD));
        covidNews.setCovidDeath(findCovidNumbers(dateExtractedExpression, CovidConst.COVID_DEATH_KEYWORD));
        covidNews.setCovidDischarge(findCovidNumbers(dateExtractedExpression, CovidConst.COVID_DISCHARGE_KEYWORD));
        covidNews.setNews(request.getCovidNews());
        covidNewsRepository.save(covidNews);
    }

    @Override
    public CovidDataResponse getCovidData(CovidDataRequest request) {
        List<CovidNews> covidNewsList;
        if (request.isCumulative()) {
            if (request.getCityCode() == null || request.getCityCode().equals("-1"))
                covidNewsList = covidNewsRepository.findByDateBetween(
                        DateConverter.toDefaultLocalDate(firstCovidCase), request.getReportDate().toLocalDate());
            else
                covidNewsList = covidNewsRepository.findByDateBetweenAndCity(
                        DateConverter.toDefaultLocalDate(firstCovidCase), request.getReportDate().toLocalDate(), request.getCityCode());
        } else {
            if (request.getCityCode() == null || request.getCityCode().equals("-1"))
                covidNewsList = covidNewsRepository.findByDate(request.getReportDate().toLocalDate());
            else
                covidNewsList = covidNewsRepository.findByDateAndCity(request.getReportDate().toLocalDate(), request.getCityCode());
        }
        return toCovidDataResponse(covidNewsList);
    }

    private CovidDataResponse toCovidDataResponse(List<CovidNews> covidNewsList) {
        CovidDataResponse response = new CovidDataResponse();
        response.setTotalCovidCase(0);
        response.setTotalCovidDeath(0);
        response.setTotalCovidDischarge(0);
        for (CovidNews covidNews : covidNewsList) {
            response.setTotalCovidCase(response.getTotalCovidCase() + covidNews.getCovidCase());
            response.setTotalCovidDeath(response.getTotalCovidDeath() + covidNews.getCovidDeath());
            response.setTotalCovidDischarge(response.getTotalCovidDischarge() + covidNews.getCovidDischarge());
        }
        return response;
    }

    private String findCity(String input) {
        List<RefCity> cityList = cityRepository.findAll();
        for (RefCity city : cityList) {
            if (input.toUpperCase(new Locale("tr", "TR")).contains(city.getCityName())) {
                return city.getCityCode();
            }
        }
        return "";
    }

    private String findDate(String input) {
        Pattern pattern = Pattern.compile("(\\d{2}.\\d{2}.\\d{4})");
        Matcher m = pattern.matcher(input);
        String covidDate = "";
        if (m.find()) {
            covidDate = m.group(1);
        }
        return covidDate;
    }

    private Integer findCovidNumbers(String input, String expression) {
        String[] arrayOfSentences = input.toUpperCase(new Locale("tr", "TR")).split("\\.");
        for (String sentences : arrayOfSentences) {
            if (sentences.contains(expression)) {
                try {
                    return Integer.valueOf(sentences.replaceAll("[^-?0-9]+", ""));
                } catch (NumberFormatException e) {
                    log.error("Vaka, Vefat, İyileşen Hasta Sayıları hatalı, istatistiklerin bozulmaması için 0 olarak ayarlanmıştır. {} {}", input, e);
                }
            }
        }
        return 0;
    }
}
