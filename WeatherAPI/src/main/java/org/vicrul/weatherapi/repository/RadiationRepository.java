package org.vicrul.weatherapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vicrul.weatherapi.domain.Radiation;

public interface RadiationRepository extends JpaRepository<Radiation, Integer> {

}
