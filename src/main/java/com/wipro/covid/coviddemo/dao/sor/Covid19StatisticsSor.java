package com.wipro.covid.coviddemo.dao.sor;

import com.wipro.covid.coviddemo.model.CovidStatsResponse;
import com.wipro.covid.coviddemo.model.CovidTotalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign Client for rapidapi Service.
 */
@Service
@FeignClient(name = "Covid19StatisticsService", url = "${client.feign.configs.rapidapi.covid.baseUrl}")
public interface Covid19StatisticsSor {

	/**
	 * This method will invoke rapidapi for totals.
	 * @return {@link CovidTotalResponse} totals.
	 */
	@GetMapping(value = "/total")
	CovidTotalResponse getTotal(@RequestHeader("x-rapidapi-host") String host, @RequestHeader("x-rapidapi-key") String key, @RequestParam("country") String country);

	/**
	 * TThis method will invoke rapidapi for stats.
	 * @return {@link CovidStatsResponse} stats.
	 */
	@GetMapping(value = "/stats/{county}")
    CovidStatsResponse getStats(@RequestHeader("x-rapidapi-host") String host, @RequestHeader("x-rapidapi-key") String key, @RequestParam("country") String country);


}
