package org.vicrul.weatherapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.vicrul.weatherapi.api.AllDataRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractData {

	protected final AllDataRepo allDataRepo;
	
	protected List<String> getMetrics(String dateStart, String dateEnd) {
		List<String> metrics = null;

		try {
			metrics = allDataRepo.getTemperature(dateStart, dateEnd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (metrics == null) {
				metrics = new ArrayList<String>();
			}
		}
		return metrics;
	}

	protected boolean compareDates(String dataStart, String dataEnd) {
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

	protected String parseDate(String date) {
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
