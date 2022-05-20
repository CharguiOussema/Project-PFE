package com.iset.projetPFE.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iset.projetPFE.entites.ChargeHoraireEffectif;
import com.iset.projetPFE.entites.Departement;
import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.Fonction;
import com.iset.projetPFE.entites.Grade;
import com.iset.projetPFE.entites.HeurePFE;
import com.iset.projetPFE.entites.Mail;
import com.iset.projetPFE.services.EnseignantService;
import com.iset.projetPFE.services.EnseignantServices;

@RestController
@RequestMapping("/Enseignant")
public class EnseignantControllers {

	@Autowired
	private EnseignantService enseignantService;

	@GetMapping(path="/getAll")
	public List<Enseignant> getAllEnseignant(){
		return enseignantService.getAllEnseignant();
	}
	
	@GetMapping(path="/getById/{id}")
	public ResponseEntity<Enseignant> findEnseignantById(@PathVariable int id){
		Enseignant enseignant = enseignantService.findEnseignantById(id);
		if(enseignant == null) {
			
			return new ResponseEntity<Enseignant>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Enseignant>(enseignant, HttpStatus.OK);
		}
	}
	
	@GetMapping(path="findByEmail/{email}")
	public ResponseEntity<Enseignant> findByEmail(@PathVariable String email){
		Enseignant enseignant = enseignantService.findByEmail(email);
		if(enseignant == null) {
			return new ResponseEntity<Enseignant>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Enseignant>(enseignant, HttpStatus.OK);
		}
	}
	
	@GetMapping(path="findByActive/{active}")
	public ResponseEntity<List<Enseignant>> findByActive(@PathVariable String active){
		List<Enseignant> enseignants = enseignantService.findByActive(active);
		if(enseignants.isEmpty()) {
			return new ResponseEntity<List<Enseignant>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<Enseignant>>(enseignants, HttpStatus.OK);
		}
	}
	


	
	@PostMapping(path="/add")
	public Enseignant createEnseignant(@RequestBody Enseignant enseignant) {
		return enseignantService.createEnseignant(enseignant);
	}
	
	@PutMapping(path="/update")
	public Enseignant updateEnseignant(@RequestBody Enseignant enseignant) {
		return enseignantService.updateEnseignant(enseignant);
	}
	
	@DeleteMapping(path="/delete/{id}")
	public void deleteEnseignant(@PathVariable int id) {
		enseignantService.deleteEnseignant(id);
	}
	
	@PostMapping(path ="readEnseignantExcel")
	public void readEnseignantsFileExcel(@RequestParam("file") MultipartFile file) {
		try {
			enseignantService.readEnseignantsFileExcel(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping(path="addHeurePFE/{id}")
	public Enseignant addHeurePFE(@PathVariable int id, @RequestBody HeurePFE heurePFE) {
		return enseignantService.addHeurePFE(id, heurePFE);
	}
	@PostMapping(path="addHoraireSemster1/{id}")
	public Enseignant addHoraireSemster1(@PathVariable int id,@RequestBody ChargeHoraireEffectif chargeHoraireEffectif) {
		return enseignantService.addHoraireSemster1(id, chargeHoraireEffectif);
	}
	@PostMapping(path="addHoraireSemster2/{id}")
	public Enseignant addHoraireSemster2(@PathVariable int id,@RequestBody ChargeHoraireEffectif chargeHoraireEffectif) {
		return enseignantService.addHoraireSemster2(id, chargeHoraireEffectif);
	}
	
	
	@GetMapping(path="findHorairePFEId/{id}")
	public Enseignant findByChargeHorairePFEById(@PathVariable int id) {
		return enseignantService.findByChargeHorairePFEId(id);
	}
	@GetMapping(path="findByDepartement/{titre}")
	public List<Enseignant> findByDepartementTitre(@PathVariable String titre) {
		return enseignantService.findByDepartementTitre(titre);
	}

	@GetMapping("/download")
	public void donwloadfile( String fileName, HttpServletResponse res) throws Exception {
	
		
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}
	
	private byte[] contentOf(String fileName) throws Exception {
		
		//return Files.readAllBytes( Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
		return Files.readAllBytes( Paths.get("src/main/resources/"+fileName));
		
	}
	@PostMapping(path = "calculHeureSup")
	public Enseignant calculHeureSupEnseignant(@RequestBody Enseignant enseignant) {
		return enseignantService.calculHeureSup(enseignant);
	}
	@GetMapping(path="/writeFileHeuresSup")
	public void writeFileHeuresSup() throws IOException {
		 	enseignantService.writeFileHeuresSup();	
	}
	
	@PostMapping(path="writeFileMemoire")
	public void writeFileMemoire(@RequestBody Enseignant enseignant)  throws IOException {
		enseignantService.writeFileMemoire(enseignant);
	}
	@DeleteMapping(path="/deleteFile/{fileName}")
	public void deleteFile(@PathVariable String fileName) {
		enseignantService.deleteFile(fileName);
	}
	
	@GetMapping(path="/convertFile/{fileName}")
	public void convertFile(@PathVariable String fileName) throws Exception{
		enseignantService.convertToPDF(fileName);
	}

	@GetMapping(path="findByCodeColeur")
	public List<Enseignant> findByCodeCouleurWithJPQL(){
		return enseignantService.findByCodeCouleurWithJPQL();
	}
	@GetMapping(path="writeFileRecap")
	public void writeFileRecap() throws Exception {
		enseignantService.writeFileRecap();
	}
	@GetMapping(path="writeFilelistMemoire")
	public void writeFilelistMemoire() throws Exception{
		enseignantService.writeFilelistMemoire();
	}
	@PostMapping(path="writeFileDossierEnseignant")
	public void writeFileDossierEnseignant(@RequestBody Enseignant enseignant) throws IOException {
		enseignantService.writeFileDossierEnseignant(enseignant);
	}
	@GetMapping(path="findByFonctionTitreLike/{titre}")
	public Enseignant findByFonctionTitreLike(@PathVariable String titre) {
		return enseignantService.findByFonctionTitreLike(titre);
	}
	@PostMapping(path="/sendMail")
	public void sendMail(@RequestBody Mail mail) {
		enseignantService.sendMail(mail);
	}
	@PutMapping(path="/addPassword")
	public Enseignant addPassword(@RequestBody Enseignant enseignant) {
		return enseignantService.addPassword(enseignant);
	}
}
