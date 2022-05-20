package com.iset.projetPFE.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.iset.projetPFE.entites.Departement;
import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.EtudiantPFE;
import com.iset.projetPFE.entites.StagePFE;

public interface StagePFEServices {
	
	public List<StagePFE> findAllStagePFE();
	public StagePFE findStagePFEById(int id);
	public StagePFE createStagePFE(StagePFE stagepfe);
	public StagePFE updateStagePFE(StagePFE stagepfe);
	public void deleteStagePFE(int id);
	public void readStagePFEByExcel(MultipartFile file) throws IOException;
	public List<StagePFE> findByEnseignantId(int id);
	public List<StagePFE> findByDepartementTitre(String titre);
	public List<StagePFE> findByDepartementId(int id);
	public StagePFE affecterEtudiant1(EtudiantPFE etudiantPFE, int id);
	public StagePFE affecterEtudiant2(EtudiantPFE etudiantPFE, int id);
	

}
