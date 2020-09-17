package org.vicrul.weatherapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Temperature;
import org.vicrul.weatherapi.repository.TemperatureRepository;

@Service
public class TemperatureServiceImpl extends AbstractData implements TemperatureService {

	private final TemperatureRepository tempRepo;
	
	public TemperatureServiceImpl(AllDataRepo allDataRepo, TemperatureRepository tempRepo) {
		super(allDataRepo);
		this.tempRepo = tempRepo;
	}

	@Override
	public List<Temperature> saveTemperatures(String dataStart, String dataEnd) throws Exception {
		String dateStartForSearch = parseDate(dataStart);
		String dateEndForSearch = parseDate(dataEnd);

		if (dateStartForSearch == "" || dateEndForSearch == "") {
			throw new Exception("Введен некорректный формат даты");
		}

		if (!compareDates(dateStartForSearch, dateEndForSearch)) {
			throw new Exception("Первая переданная дата должна быть меньше второй");
		}

		List<String> apiData = getMetrics(dateStartForSearch, dateEndForSearch, OperationType.TEMPERATURE);
		List<String> dates = new ArrayList<String>();
		List<Double> metrics = new ArrayList<Double>();
		List<Temperature> temperatures = new ArrayList<Temperature>();

		for (int element = 0; element < apiData.size(); element++) {
			if (element % 2 == 0) {
				String date = apiData.get(element).substring(1, 9);
				dates.add(date);
			} else {
				Double temperature = Double.parseDouble(apiData.get(element));
				metrics.add(temperature);
			}
		}

		double averageTemperature = 0;
		double minTemperature = 0;
		double maxTemperature = 0;
		int numberOfMetricsInDay = 0;
		for (int i = 0; i < dates.size(); i++) {
			numberOfMetricsInDay++;
			
			if (i != 0 && dates.get(i).equals(dates.get(i - 1))) {
				averageTemperature += metrics.get(i);
				minTemperature = (metrics.get(i) < metrics.get(i - 1)) ? metrics.get(i) : minTemperature;
				maxTemperature = (metrics.get(i) > metrics.get(i - 1)) ? metrics.get(i) : maxTemperature;
				
				if ((i + 1) == dates.size()) {
					averageTemperature /= numberOfMetricsInDay;
					temperatures.add(new Temperature(parseDateToDB(dates.get(i)), averageTemperature, minTemperature, maxTemperature));
				}
				
				continue;
			} else if (i != 0 && !dates.get(i).equals(dates.get(i - 1))) {
				averageTemperature /= numberOfMetricsInDay;
				temperatures.add(new Temperature(parseDateToDB(dates.get(i - 1)), averageTemperature, minTemperature, maxTemperature));
				averageTemperature = metrics.get(i);
				minTemperature = metrics.get(i);
				numberOfMetricsInDay = 0;
			}
			
			averageTemperature = metrics.get(i);
			minTemperature = metrics.get(i);
		}
		tempRepo.deleteAll();
		tempRepo.saveAll(temperatures);
		return tempRepo.findAll();
	}
}
