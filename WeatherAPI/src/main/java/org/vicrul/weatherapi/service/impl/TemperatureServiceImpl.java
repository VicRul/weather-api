package org.vicrul.weatherapi.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.service.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Temperature;
import org.vicrul.weatherapi.repository.TemperatureRepository;
import org.vicrul.weatherapi.service.TemperatureService;

@Service
public class TemperatureServiceImpl extends AbstractData implements TemperatureService {

	private final TemperatureRepository tempRepo;

	public TemperatureServiceImpl(AllDataRepo allDataRepo, TemperatureRepository tempRepo) {
		super(allDataRepo);
		this.tempRepo = tempRepo;
	}

	@Override
	public List<Temperature> saveTemperatures(String dataStart, String dataEnd) {
		LocalDate dateStartForSearch = parseDate(dataStart);
		LocalDate dateEndForSearch = parseDate(dataEnd);
		int numberOfDays = (int) ChronoUnit.DAYS.between(dateStartForSearch, dateEndForSearch);
		System.out.println(tempRepo.findByDate(dateStartForSearch));

		List<String> apiData = getMetrics(dateStartForSearch, dateEndForSearch, OperationType.TEMPERATURE);
		List<Temperature> temperatures = new ArrayList<>();
		

		int numberOfMetricsInDay = 0;
		double averageTemperature = 0, minTemperature = 0, maxTemperature = 0;
		double currentTemperature = Double.parseDouble(apiData.get(1));
		String lastDate = apiData.get(0).substring(1, 9), currentDate = "";
		boolean dateIsExistInDatabase = tempRepo.findByDate(parseDate(lastDate));
		
		for (int i = 2; i < apiData.size(); i++) {
			if (dateIsExistInDatabase) {
				if ((i & 1) != 1) {
					currentDate = apiData.get(i).substring(1, 9);
					if (currentDate.equals(lastDate)) {
						continue;
					} else {
						dateIsExistInDatabase = tempRepo.findByDate(parseDate(currentDate));
						lastDate = apiData.get(i - 2).substring(1, 9);
					}
				} else {
					continue;
				}
			} else {
				if ((i & 1) != 1) {
					currentDate = apiData.get(i).substring(1, 9);
					lastDate = apiData.get(i - 2).substring(1, 9);
					if (currentDate.equals(lastDate)) {
						numberOfMetricsInDay++;
					} else {
						dateIsExistInDatabase = tempRepo.findByDate(parseDate(currentDate));
						lastDate = apiData.get(i - 2).substring(1, 9);
						averageTemperature /= numberOfMetricsInDay;
						temperatures.add(new Temperature(parseDate(lastDate), averageTemperature, minTemperature, maxTemperature));
						numberOfMetricsInDay = 0;
						averageTemperature = 0;
						lastDate = currentDate;
					} 
				} else {
					currentTemperature = Double.parseDouble(apiData.get(i));
					averageTemperature += currentTemperature;
					if (numberOfMetricsInDay <= 1) {
						minTemperature = currentTemperature;
						maxTemperature = currentTemperature;
					} else {
						maxTemperature = (maxTemperature < currentTemperature) ? currentTemperature : maxTemperature;
						minTemperature = (minTemperature > currentTemperature) ? currentTemperature : minTemperature;
						if (i == apiData.size() - 1) {
							averageTemperature /= numberOfMetricsInDay;
							temperatures.add(new Temperature(parseDate(lastDate), averageTemperature, minTemperature,
									maxTemperature));
						} 
					}
				}
			}
		}
		
		tempRepo.saveAll(temperatures);
		return tempRepo.findImportValues(numberOfDays);
	}
}
