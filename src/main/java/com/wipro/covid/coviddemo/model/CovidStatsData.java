package com.wipro.covid.coviddemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class CovidStatsData {

	private String city;
	private String province;
	private String country;
	private String lastUpdate;
	private String keyId;
	private Integer confirmed;
	private Integer deaths;
	private Integer recovered;

}