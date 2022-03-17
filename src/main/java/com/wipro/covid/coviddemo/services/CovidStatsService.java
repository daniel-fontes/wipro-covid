package com.wipro.covid.coviddemo.services;

import com.wipro.covid.coviddemo.model.CovidStatsData;
import com.wipro.covid.coviddemo.model.CovidTotalData;

import java.util.List;

public interface CovidStatsService {

    CovidTotalData getTotal(String country);


    List<CovidStatsData> getStats(String country, Integer limit, Integer page);
}
