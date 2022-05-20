package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Departement;
import com.iset.projetPFE.entites.StagePFE;
import com.iset.projetPFE.repositories.DepartementRespository;

@Service
public class DepartementService  {
	@Autowired
	private DepartementRespository departementRespository;
	
	public Departement findByTitre(String titre) {
		return departementRespository.findByTitre(titre);
	}
	public List<Departement> findAllDepartement(){
		return departementRespository.findAll();
	}
	public Departement findByEnseignantsId(int id) {
		return departementRespository.findByEnseignantsId(id);
	}
	
}
