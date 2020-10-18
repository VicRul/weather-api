package org.vicrul.weatherapi.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vicrul.weatherapi.domain.Radiation;

public interface RadiationRepository extends JpaRepository<Radiation, Integer> {

	Radiation findByDateStartAndDateEnd(LocalDate dateStart, LocalDate dateEnd);
	
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Radiation r WHERE r.dateStart = :dateStart and r.dateEnd = :dateEnd")
	boolean findByDates(LocalDate dateStart, LocalDate dateEnd);
}
