package org.vicrul.weatherapi.service;

import java.util.List;

import org.vicrul.weatherapi.domain.Temperature;

public interface TemperatureService {

	List<Temperature> saveTemperatures(String dataStart, String dataEnd) throws Exception;
}
