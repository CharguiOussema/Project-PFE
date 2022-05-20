package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Departement;

@Repository
public interface DepartementRespository extends JpaRepository<Departement, Integer> {

	public Departement findByTitre(String titre);
	public Departement findByEnseignantsId(int id);
}
