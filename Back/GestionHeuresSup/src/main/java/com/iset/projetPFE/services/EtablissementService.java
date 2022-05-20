package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Etablissement;
import com.iset.projetPFE.repositories.EtablissementRespository;

@Service
public class EtablissementService {
	
	@Autowired
	private EtablissementRespository etablissementRespository;
	
	public Etablissement createEtablissement(Etablissement etablissement) {

		
	
		return etablissementRespository.save(etablissement);
	}
	
	public Etablissement findEtablissement() {
		List<Etablissement> etablissements	=etablissementRespository.findAll();
		return etablissements.get(0);
	}

}
