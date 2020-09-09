package org.vicrul.weatherapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vicrul.weatherapi.domain.Temperature;
import org.vicrul.weatherapi.service.TemperatureService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/temp")
@AllArgsConstructor
public class TemperatureController {

	private final TemperatureService tempService;
	
	@GetMapping
	public List<Temperature> getTemperatures(@RequestParam String dateStart, @RequestParam String dateEnd) throws Exception {
		return tempService.saveMetrics(dateStart, dateEnd);
	}
}
