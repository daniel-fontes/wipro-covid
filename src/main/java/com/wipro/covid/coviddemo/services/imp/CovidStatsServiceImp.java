package com.wipro.covid.coviddemo.services.imp;

import com.wipro.covid.coviddemo.dao.Covid19StatsDao;
import com.wipro.covid.coviddemo.model.CovidStatsData;
import com.wipro.covid.coviddemo.model.CovidTotalData;
import com.wipro.covid.coviddemo.services.CovidStatsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CovidStatsServiceImp implements CovidStatsService {

    private final Covid19StatsDao covid19StatsDao;

    public CovidStatsServiceImp( Covid19StatsDao covid19StatsDao) {
        this.covid19StatsDao = covid19StatsDao;
    }

    @Override
    public CovidTotalData getTotal(String country){
        return covid19StatsDao.getTotal(country);
    }

    @Override
    public List<CovidStatsData> getStats(String country, Integer limit, Integer page) {
        return covid19StatsDao.getStats( country, limit, page);
    }


}
