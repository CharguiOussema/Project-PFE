package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Fonction;
import com.iset.projetPFE.repositories.FonctionRespository;
@Service
public class FonctionService {

	@Autowired
	private FonctionRespository fonctionRespository;
	
	public Fonction findByTitre(String titre) {
		return fonctionRespository.findByTitre(titre);
	}
	public List<Fonction> FindAllFonction(){
		return fonctionRespository.findAll();
	}
	
}
