package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Grade;

@Repository
public interface GradeRespository extends JpaRepository<Grade, Integer> {

	public Grade findByTitre(String titre);
}
