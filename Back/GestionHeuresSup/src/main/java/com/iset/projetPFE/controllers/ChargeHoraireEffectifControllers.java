package com.iset.projetPFE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.projetPFE.entites.ChargeHoraireEffectif;
import com.iset.projetPFE.services.ChargeHoraireEffectifService;

@RestController
@RequestMapping("/ChargeHoraireEffectif")
public class ChargeHoraireEffectifControllers {
	@Autowired
	private ChargeHoraireEffectifService chargeHoraireEffectifService;
	
	@GetMapping(path="findById/{id}")
	public ResponseEntity<ChargeHoraireEffectif> findById(@PathVariable int id){
		ChargeHoraireEffectif chargeHoraireEffectif = chargeHoraireEffectifService.findById(id);
		if(chargeHoraireEffectif == null) {
			return new ResponseEntity<ChargeHoraireEffectif>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<ChargeHoraireEffectif>(chargeHoraireEffectif, HttpStatus.OK);
		}
	}

}
