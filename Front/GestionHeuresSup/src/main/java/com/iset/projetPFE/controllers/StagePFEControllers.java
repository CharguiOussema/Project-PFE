package com.iset.projetPFE.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.EtudiantPFE;
import com.iset.projetPFE.entites.StagePFE;
import com.iset.projetPFE.services.StagePFEService;

@RestController
@RequestMapping("/StagePFE")
public class StagePFEControllers {
	
	@Autowired
	private StagePFEService pfeService;
	
	@GetMapping(path="getAll")
	public List<StagePFE> findAllStagePFE(){
		return pfeService.findAllStagePFE();
	}
	@GetMapping(path="findById/{id}")
	public ResponseEntity<StagePFE> findStagePFEById(@PathVariable int id) {
		StagePFE pfe = pfeService.findStagePFEById(id);
		if(pfe == null) {
			return new ResponseEntity<StagePFE>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<StagePFE>(pfe, HttpStatus.OK);
		}
	}
	@PostMapping(path="add")
	public StagePFE createStagePFE(@RequestBody StagePFE stagepfe) {
		
		return pfeService.createStagePFE(stagepfe);
	}
	
	@PutMapping(path="update")
	public StagePFE updateStagePFE(@RequestBody StagePFE stagepfe) {
		return pfeService.updateStagePFE(stagepfe);
	}
	@DeleteMapping(path="delete/{id}")
	public void deleteStagePFE(@PathVariable int id) {
		pfeService.deleteStagePFE(id);
	}
	
	@GetMapping(path ="/findByEnseignantId/{id}")
	public ResponseEntity<List<StagePFE>> findByEnseignantId(@PathVariable int id){
		List<StagePFE> pfes = pfeService.findByEnseignantId(id);
		
		if(pfes.isEmpty()) {
			return new ResponseEntity<List<StagePFE>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<StagePFE>>(pfes, HttpStatus.OK);
		}
	}
	@GetMapping(path="/findByDepartementTitre/{titre}")
	public ResponseEntity<List<StagePFE>> findByDepartementTitre(@PathVariable String titre){
		List<StagePFE> pfes = pfeService.findByDepartementTitre(titre);
		
		if(pfes.isEmpty()) {
			return new ResponseEntity<List<StagePFE>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<StagePFE>>(pfes, HttpStatus.OK);
		}
	}
	
	@PostMapping(path="readStagePFEByExcel")
	public void readStagePFEByExcel(@RequestParam("file") MultipartFile file) {
		try {
			pfeService.readStagePFEByExcel(file);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	@GetMapping(path="findBydepartementId/{id}")
	public ResponseEntity<List<StagePFE>> findBydepartementId(@PathVariable int id){
		List<StagePFE> pfes = pfeService.findByDepartementId(id);
		if(pfes.isEmpty()) {
			return new ResponseEntity<List<StagePFE>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<StagePFE>>(pfes, HttpStatus.OK);
		}
	}
	
	@PutMapping(path="affecterEtudiant1/{id}")
	public StagePFE affecterEtudiant1(@RequestBody EtudiantPFE etudiantPFE,@PathVariable int id) {
		return pfeService.affecterEtudiant1(etudiantPFE, id);
	}
	@PutMapping(path="affecterEtudiant2/{id}")
	public StagePFE affecterEtudiant2(@RequestBody EtudiantPFE etudiantPFE,@PathVariable int id) {
		return pfeService.affecterEtudiant2(etudiantPFE, id);
	}
	
}
