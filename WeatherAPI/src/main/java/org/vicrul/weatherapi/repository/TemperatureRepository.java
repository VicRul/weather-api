package org.vicrul.weatherapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vicrul.weatherapi.domain.Temperature;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer>{
	
	List<Temperature> findByDateBetween(LocalDate dateStart, LocalDate dateEnd);
	
	@Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Temperature t WHERE t.date = :date")
	boolean findByDate(LocalDate date);
	
	@Query(nativeQuery = true, value = "SELECT * FROM temperatures ORDER BY id DESC LIMIT :limit")
	List<Temperature> findImportValues(int limit);
}
