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
import com.iset.projetPFE.services.GradeService;

@RestController
@RequestMapping("/Grade")
public class GradeControllers {
	@Autowired
	private GradeService gradeService;
	

	@GetMapping(path="findGradeByTitre/{titre}")
	public ResponseEntity<Grade> findGradeByTitre(@PathVariable String titre){
		Grade grade = gradeService.findByTitre(titre);
		if(grade == null) {
			return new ResponseEntity<Grade>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Grade>(grade, HttpStatus.OK);
		}
	}
	@GetMapping(path="/getAll")
	public List<Grade> getAllGrade(){
		return gradeService.findAllGrade();
	}
}
