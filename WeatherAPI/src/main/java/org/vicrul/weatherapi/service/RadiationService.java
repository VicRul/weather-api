package org.vicrul.weatherapi.service;

import org.vicrul.weatherapi.domain.Radiation;

public interface RadiationService {
	
	Radiation getMaxRadiationLevel(String dataStart, String dataEnd) throws Exception;
}
