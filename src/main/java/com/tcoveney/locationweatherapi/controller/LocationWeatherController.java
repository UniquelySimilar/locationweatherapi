package com.tcoveney.locationweatherapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LocationWeatherController {
	private static final Logger logger = LogManager.getLogger(LocationWeatherController.class);
	private final int maxLatitude = 90;
	private final int maxLongitude = 180;

	@GetMapping("/data")
	public String getLocationWeather(@RequestParam("numLocations") int numLocations) {
		String retVal = "called 'getLocationWeather()'";
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		
		// TEMPORARY TEST
		numLocations = 10;
		
		try {
			if (numLocations < 1 || numLocations > 20) {
				throw new Exception("ERROR: Request parameter 'numLocations' must be between 1 and 20");
			}
			
			// Get random latitude values in range -90 to 90:
			String[] latitudeAry = retrieveRandomNumbers(restTemplate, numLocations, maxLatitude);
			if (null == latitudeAry) {
				throw new Exception("ERROR: Could not retrieve latitude numbers.");
			}
			
			// Get random longitude values in range -180 to 180:
			String[] longitudeAry = retrieveRandomNumbers(restTemplate, numLocations, maxLongitude);
			if (null == longitudeAry) {
				throw new Exception("ERROR: Could not retrieve longitude numbers.");
			}
			
			// TODO: Create and send weather API requests using numbers
			
			// TODO: Return weather data as JSON array
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return retVal;
	}
	
	private String[] retrieveRandomNumbers(RestTemplate restTemplate, int numLocations, int max) {
		String[] numAry = null;
		String url = "https://www.random.org/integers/?num=" + numLocations + "&min=-" + max + "&max=" + max +
				"&col=1&base=10&format=plain&rnd=new";
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode != 200) {
			logger.error("ERROR: Random number service response status: " + statusCode);
		}
		else {
			String body = responseEntity.getBody();
			logger.debug("Random number service response body: " + body);
			String delimiter = System.lineSeparator();
			numAry = body.split(delimiter);
//			for (String number : numAry) {
//				logger.debug("Number array element: " + number);
//			}
		}

		return numAry;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		// Use a timeout value of two minutes.
		int timeout = 2000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		
		return clientHttpRequestFactory;
	}
}
