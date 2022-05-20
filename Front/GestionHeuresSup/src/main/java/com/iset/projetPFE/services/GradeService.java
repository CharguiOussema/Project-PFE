package com.iset.projetPFE.services;

import java.io.IOException;
import java.util.List;


import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Grade;
import com.iset.projetPFE.repositories.GradeRespository;

@Service
public class GradeService {

	@Autowired
	private GradeRespository gradeRespository;
	
	public Grade findByTitre(String titre) {
		return gradeRespository.findByTitre(titre);
	}
	public List<Grade> findAllGrade(){
		return gradeRespository.findAll();
	}


}
