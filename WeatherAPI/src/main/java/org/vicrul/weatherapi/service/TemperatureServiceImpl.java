package org.vicrul.weatherapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.repository.AllDataRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllDataServiceImpl implements AllDataService {
	
	private final AllDataRepo allDataRepo;

	@Override
	public List<String> getTemperature(){
		List<String> temperature = null;
		
		try {
			temperature = allDataRepo.getData();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (temperature == null) {
				temperature = new ArrayList<String>();
			}
		}
		return temperature;
	}

}
