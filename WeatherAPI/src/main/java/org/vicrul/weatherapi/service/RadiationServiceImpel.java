package org.vicrul.weatherapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vicrul.weatherapi.api.AllDataRepo;
import org.vicrul.weatherapi.domain.Radiation;
import org.vicrul.weatherapi.repository.RadiationRepository;

@Service
public class RadiationServiceImpel extends AbstractData implements RadiationService {
	
	private final RadiationRepository radiationRepo;

	public RadiationServiceImpel(AllDataRepo allDataRepo, RadiationRepository radiationRepo) {
		super(allDataRepo);
		this.radiationRepo = radiationRepo;
	}

	@Override
	public List<Radiation> getMaxRadiationLevel(String dataStart, String dataEnd) throws Exception {
		
		return null;
	}

}