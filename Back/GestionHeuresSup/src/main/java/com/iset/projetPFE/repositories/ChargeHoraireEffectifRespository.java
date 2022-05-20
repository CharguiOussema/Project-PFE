package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.ChargeHoraireEffectif;
@Repository
public interface ChargeHoraireEffectifRespository extends JpaRepository<ChargeHoraireEffectif, Integer> {

	public ChargeHoraireEffectif findById(int id);
	
}
