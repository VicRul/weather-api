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
@Table(name = "radiation")
@Data
@NoArgsConstructor
public class Radiation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "date_start")
	private LocalDate dateStart;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "date_end")
	private LocalDate dateEnd;
	
	@Column(name = "rad_value")
	private double maxRadiationValue;

	public Radiation(LocalDate dateStart, LocalDate dateEnd, double maxRadiationValue) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.maxRadiationValue = maxRadiationValue;
	}	
}
