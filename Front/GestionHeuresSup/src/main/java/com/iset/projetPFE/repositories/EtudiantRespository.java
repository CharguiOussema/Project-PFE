package com.iset.projetPFE.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.EtudiantPFE;

@Repository
public interface EtudiantRespository extends JpaRepository<EtudiantPFE, Integer> {
	
	public EtudiantPFE findByCin(long cin);
	public List<EtudiantPFE> findByParcoursTitre(String titre);
	
}
