package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.Parcours;
import com.iset.projetPFE.entites.Statut;
import com.iset.projetPFE.services.StatutService;

@RestController
@RequestMapping("/Statut")
public class StatutControllers {

	@Autowired
	private StatutService statutService;
	
	@GetMapping(path="getAll")
	public List<Statut> findAllStatut(){
		return statutService.findAllStatut();
	}
	@GetMapping(path="findStatutByTitre/{titre}")
	public ResponseEntity<Statut> findStatutByTitre(@PathVariable String titre){
		Statut statut = statutService.findByTitre(titre);
		if(statut == null) {
			return new ResponseEntity<Statut>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Statut>(statut, HttpStatus.OK);
		}
	}
}
