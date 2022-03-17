package com.wipro.covid.coviddemo.dao;

import com.wipro.covid.coviddemo.model.CovidStatsData;
import com.wipro.covid.coviddemo.model.CovidTotalData;

import java.util.List;

public interface Covid19StatsDao {

    CovidTotalData getTotal(String country);

    List<CovidStatsData> getStats(String country, Integer limit, Integer page);

}
