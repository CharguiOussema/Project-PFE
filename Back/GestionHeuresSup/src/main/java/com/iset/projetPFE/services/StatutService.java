package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Statut;
import com.iset.projetPFE.repositories.StatutRespository;

@Service
public class StatutService {

	@Autowired
	private StatutRespository statutRespository;
	
	public Statut findByTitre(String titre) {
		return statutRespository.findByTitre(titre);
	}
	public List<Statut> findAllStatut(){
		return statutRespository.findAll();
	}
	
	
}
