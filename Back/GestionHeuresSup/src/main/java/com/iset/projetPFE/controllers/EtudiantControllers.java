package com.iset.projetPFE.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.EtudiantPFE;
import com.iset.projetPFE.services.EtudiantService;

@RestController
@RequestMapping("/Etudiant")
public class EtudiantControllers {
	@Autowired
	private EtudiantService etudiantService;

	@GetMapping(path="/getAll")
	public List<EtudiantPFE> findAllEtudiant(){
		return etudiantService.findAllEtudiant();
	}
	@GetMapping(path="/findEtudiantByCin/{cin}")
	public ResponseEntity<EtudiantPFE> findEtudiantByCin(@PathVariable long cin){
		EtudiantPFE etudiantPFE = etudiantService.findByCin(cin);
		if(etudiantPFE == null) {
			return new ResponseEntity<EtudiantPFE>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<EtudiantPFE>(etudiantPFE, HttpStatus.OK);
		}
	}
	@GetMapping(path="/findByParcoursTitre/{titre}")
	public ResponseEntity<List<EtudiantPFE>> findByParcoursTitre(@PathVariable String titre){
		List<EtudiantPFE> etudiantPFEs = etudiantService.findByParcoursTitre(titre);
		if(etudiantPFEs.isEmpty())
		{
			return new ResponseEntity<List<EtudiantPFE>>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<List<EtudiantPFE>>(etudiantPFEs, HttpStatus.OK);
			}
		}
	@PostMapping(path="addEtudiant")
	public EtudiantPFE addEtudiant(@RequestBody EtudiantPFE etudiantPFE) {
		return etudiantService.createEtudiant(etudiantPFE);
	}
}
