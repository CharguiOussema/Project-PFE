package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.Fonction;
import com.iset.projetPFE.entites.Grade;
import com.iset.projetPFE.services.FonctionService;

@RestController
@RequestMapping("/Fonction")
public class FonctionControllers {
	@Autowired
	private FonctionService fonctionService;
	
	
	@GetMapping(path="findFonctionByTitre/{titre}")
	public ResponseEntity<Fonction> findFonctionByTitre(@PathVariable String titre){
		Fonction fonction = fonctionService.findByTitre(titre);
		if(fonction == null) {
			return new ResponseEntity<Fonction>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Fonction>(fonction, HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/getAll")
	public List<Fonction> findAllFonction(){
		return fonctionService.FindAllFonction();
	}

}
