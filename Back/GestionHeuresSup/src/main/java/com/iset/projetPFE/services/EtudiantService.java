package com.iset.projetPFE.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.EtudiantPFE;
import com.iset.projetPFE.repositories.EtudiantRespository;

@Service
public class EtudiantService {

	@Autowired
	private EtudiantRespository etudiantRespository;
	
	public EtudiantPFE findByCin(long cin) {
		return etudiantRespository.findByCin(cin);
	}
	public List<EtudiantPFE> findAllEtudiant(){
		return etudiantRespository.findAll();
	}
	public List<EtudiantPFE> findByParcoursTitre(String titre){
		return etudiantRespository.findByParcoursTitre(titre);
	}
	public EtudiantPFE createEtudiant(EtudiantPFE etudiantPFE) {
		return etudiantRespository.save(etudiantPFE);
	}
	
}
