package org.vicrul.weatherapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vicrul.weatherapi.domain.Temperature;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer>{
	
}
