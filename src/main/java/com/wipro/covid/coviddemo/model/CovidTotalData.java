package com.wipro.covid.coviddemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class CovidTotalData {
	private Integer recovered;
	private Integer deaths;
	private Integer confirmed;
	private String lastChecked;
	private String lastReported;
	private String location;

}