package org.vicrul.weatherapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vicrul.weatherapi.service.AllDataService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/temp")
@AllArgsConstructor
public class AllDataController {

	private final AllDataService allDataService;
	
	@GetMapping
	public List<String> showTemperature() {
		return allDataService.getTemperature();
	}
}
