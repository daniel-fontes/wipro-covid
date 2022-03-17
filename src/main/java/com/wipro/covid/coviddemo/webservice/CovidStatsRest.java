package com.wipro.covid.coviddemo.webservice;

import com.wipro.covid.coviddemo.model.CovidStatsData;
import com.wipro.covid.coviddemo.model.CovidTotalData;
import com.wipro.covid.coviddemo.services.CovidStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CovidStatsRest {

    private final CovidStatsService covidStatsService;

    @Autowired
    public CovidStatsRest(CovidStatsService covidStatsService){
        this.covidStatsService = covidStatsService;
    }

    /**
     * Retrieve totals by country or global by default.
     * @param country {@link String } Country name.
     * @return {@link CovidTotalData} Total.
     */
    @GetMapping("covid-stats/total")
    public CovidTotalData getTotal(@RequestParam(name="country", required = false, defaultValue = "") String country){

        return covidStatsService.getTotal(country);

    }

    /**
     * Retrieve stats by country or global by default with pagination option.
     * @param country {@link String } Country name.
     * @param limit {@link Integer} Record per page Limit.
     * @param page {@link Integer} Page number.
     * @return {@link List<CovidStatsData>} Covid Stats List.
     */
    @GetMapping("covid-stats/{country}")
    public List<CovidStatsData> getByCountry(@RequestParam(name="country", required = false, defaultValue = "") String country,
                                             @RequestParam(name="limit", required = false) Integer limit,
                                             @RequestParam(name="page", required = false) Integer page){

        return covidStatsService.getStats(country, limit, page);

    }


}
