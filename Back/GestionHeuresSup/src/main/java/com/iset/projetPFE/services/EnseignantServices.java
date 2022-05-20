package com.iset.projetPFE.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.Mail;


public interface EnseignantServices extends UserDetailsService  {
	
	public List<Enseignant> getAllEnseignant();
	public Enseignant findEnseignantById(int id);
	public Enseignant findByCin(long cin);
	public Enseignant createEnseignant(Enseignant enseignant);
	public Enseignant updateEnseignant(Enseignant enseignant);
	public void deleteEnseignant(int id);
	public Enseignant findByEmail(String email);
	public List<Enseignant> findByActive(String active);
	public void readEnseignantsFileExcel(MultipartFile file) throws IOException;
	public Enseignant findByChargeHorairePFEId(int id);
	public List<Enseignant> findByDepartementTitre(String titre);
	public Enseignant calculHeureSup(Enseignant enseignant);
	public void writeFileHeuresSup() throws IOException ;  
	public void deleteFile(String fileName);
	public void convertToPDF(String fileName) throws Exception;
	public Enseignant initialiserChargeHoraire(Enseignant enseignant);
	public void writeFileMemoire(Enseignant enseignant) throws IOException ;  
	public List<Enseignant> findByCodeCouleurWithJPQL();
	public void writeFileRecap() throws Exception;
	public void writeFilelistMemoire()throws Exception;
	public void writeFileDossierEnseignant(Enseignant enseignant)throws IOException;
	public Enseignant findByFonctionTitreLike(String titre);
	public void sendMail(Mail mail);
	public Enseignant addPassword(Enseignant enseignant);
}
