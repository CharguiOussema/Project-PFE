package com.iset.projetPFE.services;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iset.projetPFE.ConstantsExcel.StageExcelConstants;
import com.iset.projetPFE.entites.Departement;
import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.EtudiantPFE;
import com.iset.projetPFE.entites.Parcours;
import com.iset.projetPFE.entites.StagePFE;
import com.iset.projetPFE.entites.Statut;
import com.iset.projetPFE.repositories.DepartementRespository;
import com.iset.projetPFE.repositories.EnseignantRespository;
import com.iset.projetPFE.repositories.EtudiantRespository;
import com.iset.projetPFE.repositories.ParcoursRespository;
import com.iset.projetPFE.repositories.StagePFERespository;
import com.iset.projetPFE.repositories.StatutRespository;

@Service
public class StagePFEService implements StagePFEServices {

	@Autowired
	private StagePFERespository stagePFERespository;
	@Autowired
	private EtudiantService etudiantService; 
	@Autowired
	private EnseignantService enseignantService;
	@Autowired
	private StatutService statutService;
	@Autowired 
	private ParcoursService parcoursService; 
	@Autowired
	private DepartementService departementService;
	
	
	
	@Override
	public List<StagePFE> findAllStagePFE() {
		return stagePFERespository.findAll();
	}

	@Override
	public StagePFE findStagePFEById(int id) {
		Optional<StagePFE> optional = stagePFERespository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}

	@Override
	public StagePFE createStagePFE(StagePFE stagepfe) {
		return stagePFERespository.save(stagepfe);
	}

	@Override
	public StagePFE updateStagePFE(StagePFE stagepfe) {
		Optional<StagePFE> optional = stagePFERespository.findById(stagepfe.getId());
		if(optional.isEmpty()) {
			return null;
		}else {
			return stagePFERespository.save(stagepfe);
		}
	}
	
	public StagePFE affecterEtudiant1(EtudiantPFE etudiantPFE, int id) {
		StagePFE stagepfe = findStagePFEById(id);
		stagepfe.setEtudiantPFE1(etudiantPFE);
		return stagePFERespository.save(stagepfe);
	}
	public StagePFE affecterEtudiant2(EtudiantPFE etudiantPFE, int id) {
		StagePFE stagepfe = findStagePFEById(id);
		stagepfe.setEtudiantPFE2(etudiantPFE);
		return stagePFERespository.save(stagepfe);
	}


	@Override
	public void deleteStagePFE(int id) {
		stagePFERespository.deleteById(id);
	}





	@Override
	public List<StagePFE> findByEnseignantId(int id) {
		return stagePFERespository.findByEnseignantId(id);
	}

	@Override
	public List<StagePFE> findByDepartementTitre(String titre) {
		return stagePFERespository.findByDepartementTitre(titre);
	}

	
	@Override
	public void readStagePFEByExcel(MultipartFile file) throws IOException {
		XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());	
		// XSSFSheet sheet = workbook.getSheet("0");
		 XSSFSheet sheet = workbook.getSheetAt(0);
		 int rows=sheet.getLastRowNum();
		// int cols=sheet.getRow(7).getLastCellNum();
		 for(int r=7;r<=rows;r++)
		 {
			 StagePFE stage = new StagePFE();
			 XSSFRow row = sheet.getRow(r);
			 // Etudiant 1 
			 EtudiantPFE etudiantPFE1 = new EtudiantPFE();
			 //Nom etudiant 1
			 XSSFCell nomEtudiant1 = row.getCell(StageExcelConstants.nomEtudiant1);
			 etudiantPFE1.setPrenomNom(nomEtudiant1.getStringCellValue());
			 // Cin etudiant 1
			 XSSFCell cin1 = row.getCell(StageExcelConstants.cinEtudiant1);
			 etudiantPFE1 = etudiantService.findByCin((long)cin1.getNumericCellValue());
			
			 stage.setEtudiantPFE1(etudiantPFE1);
			
			 // Etudiant 2
			 EtudiantPFE etudiantPFE2 = new EtudiantPFE();
			 // Nom etudiant 2
			 XSSFCell nomEtudiant2 = row.getCell(StageExcelConstants.nomEtudiant2);
			 etudiantPFE2.setPrenomNom(nomEtudiant2.getStringCellValue());
			 //Cin etudiant 2 
			 XSSFCell cin2 = row.getCell(StageExcelConstants.cinEtudiant2);
			 etudiantPFE2 = etudiantService.findByCin((long)cin2.getNumericCellValue());
			 stage.setEtudiantPFE2(etudiantPFE2);
			 // Enseignant 
			Enseignant enseignant = new Enseignant();
			XSSFCell nomEnseignant = row.getCell(StageExcelConstants.nomEnseignant);
			enseignant.setPrenomNom(nomEnseignant.getStringCellValue());
			// Cin Etudiant
			XSSFCell cinEnseignant = row.getCell(StageExcelConstants.cinEnseignant);
			enseignant = enseignantService.findByCin((long)cinEnseignant.getNumericCellValue());
			stage.setEnseignant(enseignant);
			
			 
			 // Sujet 
			 XSSFCell sujet = row.getCell(StageExcelConstants.sujet);
			 stage.setSujet(sujet.getStringCellValue());
			 //Société
			 XSSFCell societe = row.getCell(StageExcelConstants.societe);
			 stage.setSociete(societe.getStringCellValue());
			 //departement
			 XSSFCell departrment = row.getCell(StageExcelConstants.departement);
			 stage.setDepartement(departementService.findByTitre(departrment.getStringCellValue()));
			 //Statut
			 XSSFCell statut = row.getCell(StageExcelConstants.staut);
			 stage.setStatut(statutService.findByTitre(statut.getStringCellValue()));
			  stagePFERespository.save(stage);
		 }
		
	}



	@Override
	public List<StagePFE> findByDepartementId(int id) {
		return stagePFERespository.findByDepartementId(id);
	}
}
