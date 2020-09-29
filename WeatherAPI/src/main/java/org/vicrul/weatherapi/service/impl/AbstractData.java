package org.vicrul.weatherapi.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.vicrul.weatherapi.service.api.AllDataRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractData {

	protected final AllDataRepo allDataRepo;

	protected List<String> getMetrics(Date dateStart, Date dateEnd, OperationType type) {
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

	protected void compareDates(Date dataStart, Date dataEnd) throws DateTimeException {
		if (dataStart.compareTo(dataEnd) > 0) {
			throw new DateTimeException("Дата начала периода больше даты окончания периода");
		}
	}

	protected Date parseDate(String date) {
		Date dateFromString = null, resultDate = null;
		System.out.println(date);

		try {
			dateFromString = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			resultDate = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(dateFromString.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	protected Date parseDateToDB(String date) {
		Date dateFromString = null;
		try {
			dateFromString = new SimpleDateFormat("yyyyMMdd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFromString;
	}
}
