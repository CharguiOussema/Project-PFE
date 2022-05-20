package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.Reclamation;
import com.iset.projetPFE.services.ReclamationService;

@RestController
@RequestMapping("Reclamtion")
public class ReclamationControllers {

	@Autowired
	private ReclamationService reclamationService;
	
	@GetMapping(path="/findAll")
	public List<Reclamation> findAll(){
		return reclamationService.findAll();
	}
	@GetMapping(path="/findByTypeReclamation/{type}")
	public List<Reclamation> findByTypeReclamation(@PathVariable String type){
		return reclamationService.findByTypeReclamationTypeLike(type);
	}
	@DeleteMapping(path="/deleteReclamation/{id}")
	public void deleteReclamation(@PathVariable int id) {
		 reclamationService.deleteById(id);
	}
	@PostMapping(path="/addReclamation")
	public Reclamation addReclamation(@RequestBody Reclamation reclamation) {
		System.err.println("test add reclamation");
		return reclamationService.addReclamation(reclamation);
	}

	@GetMapping(path="/findById/{id}")
	public ResponseEntity<Reclamation> findById(@PathVariable int id){
		Reclamation reclamation = reclamationService.findById(id);
		if(reclamation == null) {
			return new ResponseEntity<Reclamation>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Reclamation>(reclamation, HttpStatus.OK);
		}
	}
	@GetMapping(path="findByDeuxType/{type1}/{type2}")
	public List<Reclamation> findByDeuxType(@PathVariable String type1,@PathVariable String type2){
		return reclamationService.findByDeuxType(type1, type2);
	}
	@GetMapping(path="findByDepartementAndDeuxType/{titre}/{type1}/{type2}")
	public List<Reclamation> findByDepartementAndDeuxType(@PathVariable String titre,@PathVariable String type1,@PathVariable String type2){
		return reclamationService.findByDepartementAndDeuxType(titre, type1, type2);
	}
}
