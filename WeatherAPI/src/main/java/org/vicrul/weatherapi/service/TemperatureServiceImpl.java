package org.vicrul.weatherapi.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Temperature;
import org.vicrul.weatherapi.repository.TemperatureRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

	private final AllDataRepo allDataRepo;
	private final TemperatureRepo tempRepo;

	private List<String> getTemperature(String dataStart, String dataEnd) {
		List<String> temperature = null;

		try {
			temperature = allDataRepo.getData(dataStart, dataEnd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (temperature == null) {
				temperature = new ArrayList<String>();
			}
		}
		return temperature;
	}

	private boolean compareDates(String dataStart, String dataEnd) {
		Date firstDate = null;
		Date secondDate = null;

		try {
			firstDate = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(dataStart);
			secondDate = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(dataEnd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return firstDate.compareTo(secondDate) < 0;
	}

	private String parseDate(String date) {
		Date dateFromString = null;
		String resultDate = "";

		try {
			dateFromString = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
			resultDate = format.format(dateFromString).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	@Override
	public List<Temperature> saveMetrics(String dataStart, String dataEnd) throws Exception {

		String dateStartForSearch = parseDate(dataStart);
		String dateEndForSearch = parseDate(dataEnd);

		if (dateStartForSearch == "" || dateEndForSearch == "") {
			throw new Exception("Введен некорректный формат даты");
		}

		if (!compareDates(dateStartForSearch, dateEndForSearch)) {
			throw new Exception("Первая переданная дата должна быть меньше второй");
		}

		System.out.println(dateStartForSearch + " " + dateEndForSearch);

		List<String> apiData = getTemperature(dateStartForSearch, dateEndForSearch);

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

		System.out.println(dates);

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
					temperatures.add(new Temperature(dates.get(i), averageTemperature, minTemperature, maxTemperature));
				}
				continue;
			} else if (i != 0 && !dates.get(i).equals(dates.get(i - 1))) {
				averageTemperature /= numberOfMetricsInDay;
				temperatures.add(new Temperature(dates.get(i - 1), averageTemperature, minTemperature, maxTemperature));
				averageTemperature = metrics.get(i);
				minTemperature = metrics.get(i);
				numberOfMetricsInDay = 0;
			}
			averageTemperature = metrics.get(i);
			minTemperature = metrics.get(i);
		}
		tempRepo.saveAll(temperatures);
		return tempRepo.findAll();
	}
}
