package org.vicrul.weatherapi.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "avg_temp")
	private double averageTemperature;
	
	@Column(name = "min_temp")
	private double minTemperature;

	@Column(name = "max_temp")
	private double maxTemperature;

	public Temperature(LocalDate date, double averageTemperature, double minTemperature, double maxTemperature) {
		this.date = date;
		this.averageTemperature = averageTemperature;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
	}
}
