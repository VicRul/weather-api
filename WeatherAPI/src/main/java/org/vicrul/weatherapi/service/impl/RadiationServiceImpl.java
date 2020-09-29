package org.vicrul.weatherapi.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.service.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Radiation;
import org.vicrul.weatherapi.repository.RadiationRepository;
import org.vicrul.weatherapi.service.RadiationService;

@Service
public class RadiationServiceImpl extends AbstractData implements RadiationService {

	private final RadiationRepository radiationRepo;

	public RadiationServiceImpl(AllDataRepo allDataRepo, RadiationRepository radiationRepo) {
		super(allDataRepo);
		this.radiationRepo = radiationRepo;
	}

	@Override
	public Radiation getMaxRadiationLevel(String dataStart, String dataEnd) {
		LocalDate dateStartForSearch = parseDate(dataStart);
		LocalDate dateEndForSearch = parseDate(dataEnd);
		compareDates(dateStartForSearch, dateEndForSearch);
		List<String> apiData = getMetrics(dateStartForSearch, dateEndForSearch, OperationType.RADIATION);

		double maxRadiationValue = Double.parseDouble(apiData.get(1));
		for (int metric = 3; metric < apiData.size(); metric++) {
			if (metric % 2 != 0) {
				double curentMetric = Double.parseDouble(apiData.get(metric));
				maxRadiationValue = (curentMetric > maxRadiationValue) ? curentMetric : maxRadiationValue;
			}
		}
		
		Radiation maxRadiation = new Radiation(dateStartForSearch, dateEndForSearch, maxRadiationValue);
		radiationRepo.save(maxRadiation);
		return radiationRepo.findTopByDateStartAndDateEnd(dateStartForSearch, dateEndForSearch);
	}

}