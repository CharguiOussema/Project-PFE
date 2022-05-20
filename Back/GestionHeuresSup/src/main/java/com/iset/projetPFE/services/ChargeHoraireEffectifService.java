package com.iset.projetPFE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.ChargeHoraireEffectif;
import com.iset.projetPFE.repositories.ChargeHoraireEffectifRespository;

@Service
public class ChargeHoraireEffectifService {

	@Autowired
	private ChargeHoraireEffectifRespository chargeHoraireEffectifRespository;
	
	public ChargeHoraireEffectif findById(int id) {
		return chargeHoraireEffectifRespository.findById(id);
	}
}
