package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Parcours;

@Repository
public interface ParcoursRespository extends JpaRepository<Parcours, Integer> {
	
	public Parcours findByTitre(String titre);

}
