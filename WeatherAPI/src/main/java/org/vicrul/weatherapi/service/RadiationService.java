package org.vicrul.weatherapi.service;

import java.util.List;

import org.vicrul.weatherapi.domain.Radiation;

public interface RadiationService {
	
	List<Radiation> getMaxRadiationLevel(String dataStart, String dataEnd) throws Exception;
}
