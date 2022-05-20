package com.iset.projetPFE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.Etablissement;
import com.iset.projetPFE.services.EtablissementService;

@RestController
@RequestMapping("Etablissement")
public class EtablissementControllers {

	@Autowired
	private EtablissementService etablissementService;
	
	@GetMapping(path="/get")
	public Etablissement getEtablissement() {
		return etablissementService.findEtablissement();
	}
	@PostMapping(path="/addEtablissement")
	public Etablissement addEtablissement(@RequestBody Etablissement etablissement) {
		return etablissementService.createEtablissement(etablissement);
	}
	
}
