package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.TypeReclamation;
import com.iset.projetPFE.repositories.TypeReclamationRespository;

@Service
public class TypeReclamationService {
	@Autowired
	private TypeReclamationRespository typeReclamationRespository;
	
	
	public List<TypeReclamation> findAll(){
		return typeReclamationRespository.findAll();
	}

	
}
