package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Statut;

@Repository
public interface StatutRespository extends JpaRepository<Statut, Integer> {
	
	public Statut findByTitre(String titre);

}
