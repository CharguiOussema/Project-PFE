package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.HeurePFE;
import com.iset.projetPFE.services.HeurePFEService;

@RestController
@RequestMapping("/HeurePFE")
public class HeurePFEControllers {

	@Autowired
	private HeurePFEService heurePFEService;
	
	
	@GetMapping(path="findById/{id}")
	public ResponseEntity<HeurePFE> findById(@PathVariable int id){
		HeurePFE heurePFE = heurePFEService.findById(id);
		if(heurePFE == null) {
			return new ResponseEntity<HeurePFE>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<HeurePFE>(heurePFE, HttpStatus.OK);
		}
	}
}
