package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.Grade;
import com.iset.projetPFE.entites.Parcours;
import com.iset.projetPFE.services.ParcoursService;

@RestController
@RequestMapping("/Parcours")
public class ParcoursControllers {

	@Autowired
	private ParcoursService parcoursService;
	
	@GetMapping(path="/getAll")
	private List<Parcours> getAll(){
		return parcoursService.findAllParcours();
	}
	@GetMapping(path="findParcoursByTitre/{titre}")
	public ResponseEntity<Parcours> findParcoursByTitre(@PathVariable String titre){
		Parcours parcours = parcoursService.findByTitre(titre);
		if(parcours == null) {
			return new ResponseEntity<Parcours>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Parcours>(parcours, HttpStatus.OK);
		}
	}
}
