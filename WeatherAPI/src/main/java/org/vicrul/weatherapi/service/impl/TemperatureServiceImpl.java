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

		List<String> apiData = getMetrics(dateStartForSearch, dateEndForSearch, OperationType.TEMPERATURE);
		List<Temperature> temperatures = new ArrayList<>();

		int numberOfMetricsInDay = 1;
		String lastDate = apiData.get(0).substring(1, 9);
		double firsMetric = Double.parseDouble(apiData.get(1));
		double averageTemperature = 0, minTemperature = firsMetric, maxTemperature = firsMetric;

		for (int i = 2; i < apiData.size(); i++) {
			double currentTemperature = 0;
			if (i % 2 == 0) {
				String currentDate = apiData.get(i).substring(1, 9);
				if (currentDate.equals(lastDate)) {
					numberOfMetricsInDay++;
				} else {
					averageTemperature /= numberOfMetricsInDay;
					temperatures.add(new Temperature(parseDate(lastDate), averageTemperature, minTemperature, maxTemperature));
					currentTemperature = Double.parseDouble(apiData.get(i + 1));
					lastDate = currentDate;
					averageTemperature = 0;
					minTemperature = currentTemperature;
					maxTemperature = currentTemperature;
					numberOfMetricsInDay = 1;
				}
			} else {
				currentTemperature = Double.parseDouble(apiData.get(i));
				averageTemperature += currentTemperature;
				maxTemperature = (maxTemperature < currentTemperature) ? currentTemperature : maxTemperature;
				minTemperature = (minTemperature > currentTemperature) ? currentTemperature : minTemperature;
				if (i == apiData.size() - 1) {
					averageTemperature /= numberOfMetricsInDay;
					temperatures.add(new Temperature(parseDate(lastDate), averageTemperature, minTemperature, maxTemperature));					
				}
			}
		}
		tempRepo.saveAll(temperatures);
		return tempRepo.findImportValues(numberOfDays);
	}
}
