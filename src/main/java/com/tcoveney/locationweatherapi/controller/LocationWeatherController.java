package com.tcoveney.locationweatherapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationWeatherController {
	@GetMapping("/data")
	public String getLocationWeather() {
		return "called 'getLocationWeather()'";
	}

}
