package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Fonction;

@Repository
public interface FonctionRespository extends JpaRepository<Fonction, Integer> {

	public Fonction findByTitre(String titre);
}
