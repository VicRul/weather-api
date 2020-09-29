package org.vicrul.weatherapi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.service.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Radiation;
import org.vicrul.weatherapi.repository.RadiationRepository;
import org.vicrul.weatherapi.service.RadiationService;

@Service
public class RadiationServiceImpel extends AbstractData implements RadiationService {
	
	private final RadiationRepository radiationRepo;

	public RadiationServiceImpel(AllDataRepo allDataRepo, RadiationRepository radiationRepo) {
		super(allDataRepo);
		this.radiationRepo = radiationRepo;
	}

	@Override
	public Radiation getMaxRadiationLevel(String dataStart, String dataEnd) throws Exception {
		Date dateStartForSearch = parseDate(dataStart);
		Date dateEndForSearch = parseDate(dataEnd);
		compareDates(dateStartForSearch, dateEndForSearch);
		List<String> apiData = getMetrics(dateStartForSearch, dateEndForSearch, OperationType.RADIATION);
		List<String> dates = new ArrayList<String>();
		List<Double> metrics = new ArrayList<Double>();

		for (int element = 0; element < apiData.size(); element++) {
			if (element % 2 == 0) {
				String date = apiData.get(element).substring(1, 9);
				dates.add(date);
			} else {
				Double radiation = Double.parseDouble(apiData.get(element));
				metrics.add(radiation);
			}
		}
		
		double maxRadiationValue = 0;
		for (int i = 0; i < dates.size(); i++) {
			if (i == 0) {
				maxRadiationValue = metrics.get(i);
			} else {
				maxRadiationValue = (metrics.get(i) > metrics.get(i - 1)) ? metrics.get(i) : maxRadiationValue;
			}
		}
		Date dateStartToDB = parseDateToDB(dataStart);
		Date dateEndToDB = parseDateToDB(dataEnd);
		Radiation maxRadiation = new Radiation(dateStartToDB, dateEndToDB, maxRadiationValue);
		radiationRepo.save(maxRadiation);
		return radiationRepo.findTopByDateStartAndDateEnd(dateStartToDB, dateEndToDB);
	}

}