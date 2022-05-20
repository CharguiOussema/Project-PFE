package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.TypeReclamation;
import com.iset.projetPFE.services.TypeReclamationService;

@RestController
@RequestMapping("/TypeReclamation")
public class TypeReclamationControllers {
	
	@Autowired
	private TypeReclamationService typeReclamationService;;
	
	@GetMapping(path="/findAll")
	public List<TypeReclamation> findAll(){
		return typeReclamationService.findAll();
	}

}
