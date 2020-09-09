package org.vicrul.weatherapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vicrul.weatherapi.domain.Radiation;
import org.vicrul.weatherapi.service.RadiationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rad")
@AllArgsConstructor
public class RadiationController {

	private final RadiationService radiationService;
	
	@GetMapping
	public List<Radiation> getRadiationLevel(@RequestParam String dateStart, @RequestParam String dateEnd) throws Exception {
		return radiationService.getMaxRadiationLevel(dateStart, dateEnd);
	}	
}
