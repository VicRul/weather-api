package org.vicrul.weatherapi.service.impl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.vicrul.weatherapi.service.api.AllDataRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractData {

	protected final AllDataRepo allDataRepo;

	protected List<String> getMetrics(LocalDate dateStart, LocalDate dateEnd, OperationType type) {
		List<String> metrics = null;

		try {
			switch (type) {
			case TEMPERATURE:
				metrics = allDataRepo.getTemperature(dateStart.toString(), dateEnd.toString());
				break;
			case RADIATION:
				System.out.println(dateStart.toString() + "  " + dateEnd.toString());
				metrics = allDataRepo.getRadiation(dateStart.toString(), dateEnd.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (metrics == null) {
				metrics = new ArrayList<String>();
			}
		}
		return metrics;
	}

	protected void compareDates(LocalDate dataStart, LocalDate dataEnd) throws DateTimeException {
		if (dataStart.compareTo(dataEnd) > 0) {
			throw new DateTimeException("Дата начала периода больше даты окончания периода");
		}
	}

	protected LocalDate parseDate(String date) {
		LocalDate resultDate = null;
		
		try {
			resultDate = LocalDate.parse(date);
		} catch (Exception e) {
			
		} finally {
			if (resultDate == null) {
				resultDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
			}
		}
		return resultDate;
	}
}
