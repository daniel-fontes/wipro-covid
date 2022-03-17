package com.wipro.covid.coviddemo.dao.impl;

import com.wipro.covid.coviddemo.dao.Covid19StatsDao;
import com.wipro.covid.coviddemo.dao.sor.Covid19StatisticsSor;
import com.wipro.covid.coviddemo.model.CovidStatsData;
import com.wipro.covid.coviddemo.model.CovidTotalData;
import com.wipro.covid.coviddemo.model.CovidStats;
import com.wipro.covid.coviddemo.model.CovidStatsResponse;
import com.wipro.covid.coviddemo.model.CovidTotalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;

@Component
public class Covid19StatsRestDaoImpl implements Covid19StatsDao {

    private final Covid19StatisticsSor covid19StatisticsSor;

    private final String key;

    private final String host;

    @Autowired
    public Covid19StatsRestDaoImpl(Covid19StatisticsSor covid19StatisticsSor, @Value("${rapidapi.covid19.key}")String key,  @Value("${rapidapi.covid19.host}")String host) {
        this.covid19StatisticsSor = covid19StatisticsSor;
        this.key = key;
        this.host = host;
    }

    @Override
    public CovidTotalData getTotal(String country){

        CovidTotalResponse resourceResponse = covid19StatisticsSor.getTotal(host,key,country);

        return Optional.ofNullable(resourceResponse).map(CovidTotalResponse::getData).orElseThrow(()-> new RuntimeException("Invalid data returned from rapidapi.com"));

    }


    @Override
    public List<CovidStatsData> getStats(String country, Integer limit, Integer page){

        if(page == null && limit != null || page != null && limit == null ){
            throw new BadRequestException("Must provide page and limit or not provide none of them.");
        }

        CovidStatsResponse resourceResponse = covid19StatisticsSor.getStats(host,key,country);

        List<CovidStatsData> covidDataList = Optional.ofNullable(resourceResponse)
                .map(CovidStatsResponse::getData)
                .map(CovidStats::getCovid19Stats)
                .orElseThrow(()-> new RuntimeException("Invalid data returned from rapidapi.com"));


        if(page == null && limit == null){
            return covidDataList;
        }

        int end = limit * page;
        int start = end - limit;

        if(start<0){
            throw new BadRequestException("Invalid pagination");
        }
        if(end<=0 || end <= start){
            throw new BadRequestException("Invalid pagination");
        }

        if(page * limit - covidDataList.size() > covidDataList.size() && page>1){
            throw new BadRequestException("Invalid pagination");
        }

        if(end > covidDataList.size() ){
            end = covidDataList.size();
        }


        return covidDataList.subList(start, end);

    }

}
