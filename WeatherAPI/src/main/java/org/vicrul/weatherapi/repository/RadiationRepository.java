package org.vicrul.weatherapi.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vicrul.weatherapi.domain.Radiation;

public interface RadiationRepository extends JpaRepository<Radiation, Integer> {

	Radiation findTopByDateStartAndDateEnd(LocalDate dateStart, LocalDate dateEnd);
}
