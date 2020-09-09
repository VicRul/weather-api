package org.vicrul.weatherapi.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temperatures")
@Data
@NoArgsConstructor
public class Temperature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "avg_temp")
	private double averageTemperature;
	
	@Column(name = "min_temp")
	private double minTemperature;

	@Column(name = "max_temp")
	private double maxTemperature;

	public Temperature(String date, double averageTemperature, double minTemperature, double maxTemperature) {
		this.date = date;
		this.averageTemperature = averageTemperature;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
	}
}
