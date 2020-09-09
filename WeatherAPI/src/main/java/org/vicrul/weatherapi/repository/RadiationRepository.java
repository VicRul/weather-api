package org.vicrul.weatherapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.vicrul.weatherapi.domain.Radiation;

@Component
public interface RadiationRepository extends JpaRepository<Radiation, Integer> {

}
