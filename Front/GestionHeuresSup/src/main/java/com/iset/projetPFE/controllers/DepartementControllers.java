package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.Departement;
import com.iset.projetPFE.services.DepartementService;

@RestController
@RequestMapping("/Departement")
public class DepartementControllers {

	@Autowired
	private DepartementService departementService;
	
	@GetMapping (path="/getAll")
	public List<Departement> findAllDepartement(){
		return departementService.findAllDepartement();
	}
	@GetMapping(path="findDepartementByTitre/{titre}")
	public ResponseEntity<Departement> findDepartementByTitre(@PathVariable String titre){
		Departement departement = departementService.findByTitre(titre);
		if(departement == null) {
			return new ResponseEntity<Departement>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Departement>(departement, HttpStatus.OK);
		}
	}
	@GetMapping(path="findByEnseignantsId/{id}")
	public ResponseEntity<Departement> findByEnseignantsId(@PathVariable int id){
		Departement departement = departementService.findByEnseignantsId(id);
		if(departement == null) {
			return new ResponseEntity<Departement>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Departement>(departement, HttpStatus.OK);
		}
	}
	
}
