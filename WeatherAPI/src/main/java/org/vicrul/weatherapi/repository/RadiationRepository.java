package org.vicrul.weatherapi.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vicrul.weatherapi.domain.Radiation;

public interface RadiationRepository extends JpaRepository<Radiation, Integer> {

	Radiation findTopByDateStartAndDateEnd(Date dateStart, Date dateEnd);
}
