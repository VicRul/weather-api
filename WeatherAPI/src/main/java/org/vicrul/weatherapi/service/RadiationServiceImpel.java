package org.vicrul.weatherapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Radiation;
import org.vicrul.weatherapi.domain.Temperature;
import org.vicrul.weatherapi.repository.RadiationRepository;

@Service
public class RadiationServiceImpel extends AbstractData implements RadiationService {
	
	private final RadiationRepository radiationRepo;

	public RadiationServiceImpel(AllDataRepo allDataRepo, RadiationRepository radiationRepo) {
		super(allDataRepo);
		this.radiationRepo = radiationRepo;
	}

	@Override
	public List<Radiation> getMaxRadiationLevel(String dataStart, String dataEnd) throws Exception {
		String dateStartForSearch = parseDate(dataStart);
		String dateEndForSearch = parseDate(dataEnd);

		if (dateStartForSearch == "" || dateEndForSearch == "") {
			throw new Exception("Введен некорректный формат даты");
		}

		if (!compareDates(dateStartForSearch, dateEndForSearch)) {
			throw new Exception("Первая переданная дата должна быть меньше второй");
		}
		
		List<String> apiData = getMetrics(dateStartForSearch, dateEndForSearch);
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
		
		Radiation maxRadiation = new Radiation(dataStart, dataEnd, maxRadiationValue);
		radiationRepo.save(maxRadiation);
		return radiationRepo.findAll();
	}

}