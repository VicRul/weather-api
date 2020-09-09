package org.vicrul.weatherapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(name = "date_start")
	private String dateStart;

	@Column(name = "date_end")
	private String dateEnd;
	
	@Column(name = "rad_value")
	private double maxRadiationValue;

	public Radiation(String dateStart, String dateEnd, double maxRadiationValue) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.maxRadiationValue = maxRadiationValue;
	}	
}
