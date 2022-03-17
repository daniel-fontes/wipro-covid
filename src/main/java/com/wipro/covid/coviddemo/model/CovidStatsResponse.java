package com.wipro.covid.coviddemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class CovidStatsResponse {

	private CovidStats data;
	private boolean error;
	private String message;
	private int statusCode;
}