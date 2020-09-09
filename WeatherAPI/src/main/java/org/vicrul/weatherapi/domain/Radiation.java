package org.vicrul.weatherapi.domain;

import java.util.Date;

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
	private Date dateStart;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "date_end")
	private Date dateEnd;
	
	@Column(name = "rad_value")
	private double maxRadiationValue;

	public Radiation(Date dateStart, Date dateEnd, double maxRadiationValue) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.maxRadiationValue = maxRadiationValue;
	}	
}
