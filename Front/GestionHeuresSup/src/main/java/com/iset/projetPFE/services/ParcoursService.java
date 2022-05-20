package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Parcours;
import com.iset.projetPFE.repositories.ParcoursRespository;

@Service
public class ParcoursService {

	@Autowired
	private ParcoursRespository parcoursRespository;
	
	public Parcours findByTitre(String titre) {
		return parcoursRespository.findByTitre(titre);
	}
	public List<Parcours> findAllParcours(){
		return parcoursRespository.findAll();
	}
}
