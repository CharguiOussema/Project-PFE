package com.iset.projetPFE.services;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.math3.util.Precision;
import org.apache.poi.hpsf.Decimal;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlgraphics.util.DoubleFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.DefaultMessageCodesResolver.Format;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.cells.Font;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.iset.projetPFE.ConstantsExcel.ConstantsHeuresSup;
import com.iset.projetPFE.ConstantsExcel.EnseignantExcelConstants;
import com.iset.projetPFE.entites.ChargeHoraireEffectif;
import com.iset.projetPFE.entites.Departement;
import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.Etablissement;
import com.iset.projetPFE.entites.Fonction;
import com.iset.projetPFE.entites.Grade;
import com.iset.projetPFE.entites.HeurePFE;
import com.iset.projetPFE.entites.Mail;
import com.iset.projetPFE.repositories.DepartementRespository;
import com.iset.projetPFE.repositories.EnseignantRespository;
import com.iset.projetPFE.repositories.FonctionRespository;
import com.iset.projetPFE.repositories.GradeRespository;
import com.qoppa.office.ExcelConvertOptions;
import com.qoppa.office.ExcelDocument;



@Service
public class EnseignantService implements EnseignantServices {

	@Autowired
	private EnseignantRespository enseignantRespository;
	@Autowired
	private DepartementService departementService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private FonctionService fonctionService;
	@Autowired
	private EtablissementService etablissementService; 
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public List<Enseignant> getAllEnseignant() {
		return enseignantRespository.findByActive("y");
	}

	@Override
	public Enseignant findEnseignantById(int id) {
		Optional<Enseignant> optional = enseignantRespository.findById(id);
		if (optional.isEmpty()) {
			return null;
		} else {
			return optional.get();
		}
	}


	@Override
	public Enseignant createEnseignant(Enseignant enseignant) {
		
	
		/*enseignant.setPassword("pwd");
	
		String cryptedPassword = bCryptPasswordEncoder.encode(enseignant.getPassword());
		enseignant.setPassword(cryptedPassword);*/
		return enseignantRespository.save(enseignant);
	}

	@Override
	public Enseignant updateEnseignant(Enseignant enseignant) {
		Optional<Enseignant> optional = enseignantRespository.findById(enseignant.getId());
		if (optional.isEmpty()) {
			return null;
		} else {
			return enseignantRespository.save(enseignant);
		}
	}

	@Override
	public void deleteEnseignant(int id) {
		enseignantRespository.deleteById(id);
	}

	@Override
	public Enseignant findByEmail(String email) {
		Enseignant enseignant = enseignantRespository.findByEmail(email);
		if (enseignant == null)
			throw new UsernameNotFoundException(email);
		return enseignant;
	}
	@Override
	public Enseignant findByCin(long cin) {
		Enseignant enseignant = enseignantRespository.findByCin(cin);
		if(enseignant == null) {
			return null;
		}else {
			return enseignant;
		}
		
	}

	@Override
	public List<Enseignant> findByActive(String active) {

		return enseignantRespository.findByActive(active);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Enseignant enseignant = enseignantRespository.findByEmail(email);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority(enseignant.getFonction().getTitre()));

		return new User(enseignant.getEmail(), enseignant.getPassword(), authorities);
	}

	@Override
	public void readEnseignantsFileExcel(MultipartFile file) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		// XSSFSheet sheet = workbook.getSheet("0");
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getLastRowNum();
		// int cols=sheet.getRow(4).getLastCellNum();
		for (int r = 4; r <= rows; r++) {
			Enseignant enseignant = new Enseignant();
			XSSFRow row = sheet.getRow(r);

			// prenomNom
			XSSFCell prenomNom = row.getCell(EnseignantExcelConstants.prenomNom);
			enseignant.setPrenomNom(prenomNom.getStringCellValue());
			// prenomNomArabe
			XSSFCell prenomNomArabe = row.getCell(EnseignantExcelConstants.prenomNomArabe);
			enseignant.setPrenomNomArabe(prenomNomArabe.getStringCellValue());
			// Cin
			XSSFCell cin = row.getCell(EnseignantExcelConstants.cin);
			enseignant.setCin((long) cin.getNumericCellValue());
			// Identifiant Unique
			XSSFCell identifiantUnique = row.getCell(EnseignantExcelConstants.identifiantUnique);
			enseignant.setIdentifiantUnique((long) identifiantUnique.getNumericCellValue());
			// Rib
			XSSFCell rib = row.getCell(EnseignantExcelConstants.rib);
			enseignant.setRib((long) rib.getNumericCellValue());
			// numTel
			XSSFCell numTel = row.getCell(EnseignantExcelConstants.numTel);
			enseignant.setNumTel((long) numTel.getNumericCellValue());
			// email
			XSSFCell email = row.getCell(EnseignantExcelConstants.email);
			enseignant.setEmail(email.getStringCellValue());
			// grade
			XSSFCell titreGrade = row.getCell(EnseignantExcelConstants.titreGrade);
			enseignant.setGrade(gradeService.findByTitre(titreGrade.getStringCellValue()));
			// departement
			XSSFCell titreDepartement = row.getCell(EnseignantExcelConstants.titreDepartement);
			enseignant.setDepartement(departementService.findByTitre(titreDepartement.getStringCellValue()));
			// fonction
			XSSFCell titreFonction = row.getCell(EnseignantExcelConstants.titreFonction);
			enseignant.setFonction(fonctionService.findByTitre(titreFonction.getStringCellValue()));
			// dateNaissance
			XSSFCell dateNaissance = row.getCell(EnseignantExcelConstants.dateNaissance);
			enseignant.setDateNaissance(LocalDate.from(dateNaissance.getLocalDateTimeCellValue()));
			// lieuNaissance
			XSSFCell lieuNaissance = row.getCell(EnseignantExcelConstants.lieuNaissance);
			enseignant.setLieuNaissance(lieuNaissance.getStringCellValue());
			// lieuNaissanceArabe
			XSSFCell lieuNaissanceArabe = row.getCell(EnseignantExcelConstants.lieuNaissanceArabe);
			enseignant.setLieuNaissanceArabe(lieuNaissanceArabe.getStringCellValue());
			enseignant.setActive("n");
			createEnseignant(enseignant);

		}

	}

	public Enseignant addHeurePFE(int id, HeurePFE heurePFE) {
		Enseignant enseignant = findEnseignantById(id);
		enseignant.setChargeHorairePFE(heurePFE);
		enseignant = initialiserChargeHoraire(enseignant);
		calculHeureSup(enseignant);
		return enseignantRespository.save(enseignant);
	}

	public Enseignant addHoraireSemster1(int id, ChargeHoraireEffectif chargeHoraireEffectif) {
		Enseignant enseignant = findEnseignantById(id);
		
		enseignant.setChargeHoraireS1(chargeHoraireEffectif);
		enseignant = initialiserChargeHoraire(enseignant);
		calculHeureSup(enseignant);
		return enseignantRespository.save(enseignant);
	}

	public Enseignant addHoraireSemster2(int id, ChargeHoraireEffectif chargeHoraireEffectif) {
		Enseignant enseignant = findEnseignantById(id);
		enseignant.setChargeHoraireS2(chargeHoraireEffectif);
		enseignant = initialiserChargeHoraire(enseignant);
		calculHeureSup(enseignant);
		return enseignantRespository.save(enseignant);
	}

	@Override
	public Enseignant findByChargeHorairePFEId(int id) {
		return enseignantRespository.findByChargeHorairePFEId(id);
	}

	@Override
	public List<Enseignant> findByDepartementTitre(String titre) {
		return enseignantRespository.findByDepartementTitre(titre);
	}

	@Override
	public Enseignant calculHeureSup(Enseignant enseignant) {
		double ponderationTd = 0;
		double ponderationTp = 0;
		double ponderationCours = 0;
		
		ChargeHoraireEffectif chargeHeureSup = new ChargeHoraireEffectif();
		
					ConstantsHeuresSup.nombreTotalTd = ((enseignant.getChargeHoraireS1().getTd() + enseignant.getChargeHoraireS2().getTd())/2 );
			ConstantsHeuresSup.nombreTotalTp = (enseignant.getChargeHoraireS1().getTp() + enseignant.getChargeHoraireS2().getTp())/2;
			ConstantsHeuresSup.nombreTotalCours = (enseignant.getChargeHoraireS1().getCours() + enseignant.getChargeHoraireS2().getCours())/2;
			ConstantsHeuresSup.nombreTotal = (ConstantsHeuresSup.nombreTotalTd + ConstantsHeuresSup.nombreTotalTp + ConstantsHeuresSup.nombreTotalCours);
			ConstantsHeuresSup.nombreTotalAvecPFE = ConstantsHeuresSup.nombreTotal + enseignant.getChargeHorairePFE().getNombre();
			//calcul

			
			if(ConstantsHeuresSup.nombreTotal > enseignant.getGrade().getDue())
			{
				enseignant.setCodeCouleur("green");
			if(ConstantsHeuresSup.nombreTotal > (enseignant.getGrade().getDue()+enseignant.getGrade().getMaxDue())) {
					//  ! sa3at mora5as fiha 
				enseignant.setCodeCouleur("orange");}
				ponderationTd =  Precision.round(((ConstantsHeuresSup.nombreTotal - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalTd) / ConstantsHeuresSup.nombreTotal,3);
				 ponderationTp = Precision.round(((ConstantsHeuresSup.nombreTotal - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalTp) / ConstantsHeuresSup.nombreTotal,3);
				 ponderationCours = Precision.round(((ConstantsHeuresSup.nombreTotal - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalCours) / ConstantsHeuresSup.nombreTotal,3);	
			
			}else if(ConstantsHeuresSup.nombreTotalAvecPFE > enseignant.getGrade().getDue()) {
					// sa3at mora5as fiha 
				enseignant.setCodeCouleur("green");
				double restPFE = ConstantsHeuresSup.nombreTotalAvecPFE - enseignant.getGrade().getDue();
				double valAjoute = enseignant.getGrade().getDue() - ConstantsHeuresSup.nombreTotal;
				double totalAndValJoute = ConstantsHeuresSup.nombreTotal + valAjoute;
				 ponderationTd =  Precision.round(((totalAndValJoute - enseignant.getGrade().getDue()) * (ConstantsHeuresSup.nombreTotalTd + valAjoute)) / totalAndValJoute,3) ;
				 ponderationTp = Precision.round(((totalAndValJoute - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalTp) / totalAndValJoute,3);
				 ponderationCours = Precision.round(((totalAndValJoute - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalCours) / totalAndValJoute,3);	
				
			}else if(ConstantsHeuresSup.nombreTotalAvecPFE <= enseignant.getGrade().getDue()){
				if(ConstantsHeuresSup.nombreTotalAvecPFE < enseignant.getGrade().getDue()) {enseignant.setCodeCouleur("red");}
				else if(ConstantsHeuresSup.nombreTotalAvecPFE == enseignant.getGrade().getDue()) {enseignant.setCodeCouleur("white");}
				 
				 ponderationTd =  Precision.round(((ConstantsHeuresSup.nombreTotalAvecPFE - enseignant.getGrade().getDue()) * (ConstantsHeuresSup.nombreTotalTd + enseignant.getChargeHorairePFE().getNombre())) / ConstantsHeuresSup.nombreTotalAvecPFE,3) ;
				 ponderationTp = Precision.round(((ConstantsHeuresSup.nombreTotalAvecPFE - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalTp) / ConstantsHeuresSup.nombreTotalAvecPFE,3);
				 ponderationCours = Precision.round(((ConstantsHeuresSup.nombreTotalAvecPFE - enseignant.getGrade().getDue()) * ConstantsHeuresSup.nombreTotalCours) / ConstantsHeuresSup.nombreTotalAvecPFE,3);	
				 System.err.println("due = "+ enseignant.getGrade().getDue());
				 System.err.println("td = "+ponderationTd);
				 System.err.println("tp = "+ponderationTp);
				 System.err.println("cours = "+ponderationCours);
			}
			 chargeHeureSup.setCours(ponderationCours);
			 chargeHeureSup.setTd(ponderationTd);
			 chargeHeureSup.setTp(ponderationTp);
			enseignant.setHeuresSupp(chargeHeureSup);
	

		return enseignant;
	}


	@Override
	public void writeFileHeuresSup() throws IOException {
		String filePath="src/main/resources/heuresSup.xlsx";
		FileInputStream fis=new FileInputStream(filePath);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheetAt(0);
		Etablissement etablissement = new Etablissement();
		etablissement = etablissementService.findEtablissement();
		
		List<Enseignant> enseignants = new ArrayList<Enseignant>();
		enseignants = getAllEnseignant();
		int rows = enseignants.size();
		// create style border All
		CellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		CellStyle style2 = workbook.createCellStyle();
		style2.setVerticalAlignment(VerticalAlignment.TOP );
		style2.setAlignment(HorizontalAlignment.CENTER);		
		// tableau index 
			// nombre d'encadrement PFE
		XSSFRow tabNbPFE = sheet.getRow(1);
		XSSFCell nbpfe = tabNbPFE.createCell(20);
		nbpfe.setCellStyle(style);
		nbpfe.setCellValue(etablissement.getNombreProjetPFE());
		int nombreProjetPFE = etablissement.getNombreProjetPFE();
		XSSFRow tabNbEnseignant = sheet.getRow(2);
		XSSFCell nbEnseignant = tabNbEnseignant.createCell(20);
		nbEnseignant.setCellStyle(style);
		nbEnseignant.setCellValue(etablissement.getNombreEnseignant());
		int nombreEnseignant = etablissement.getNombreEnseignant();
		XSSFRow tabPourcentage = sheet.getRow(3);
		XSSFCell pourcentage = tabPourcentage.createCell(20);
		pourcentage.setCellStyle(style);
		if( etablissement.getNombreEnseignant() != 0) {
			float NBPFE= etablissement.getNombreProjetPFE();
			float NBEnseignants= etablissement.getNombreEnseignant();
			// double PourcentageEncadrement = Precision.round(nbPFE/nbEnseignants,9);
			 float PourcentageEncadrement = NBPFE/ NBEnseignants;
			System.err.println("pourcentage encadrement => "+ PourcentageEncadrement);
		pourcentage.setCellValue("%"+String.valueOf(PourcentageEncadrement));
		}
		// concatner deux colone (firstrow, lastrow, firstcol, lastcol)
		//sheet.addMergedRegion(new CellRangeAddress(3,3,20,21));
		// année univrsitaire 
		XSSFRow anneeUniverstaire = sheet.getRow(7);
		XSSFCell anne = anneeUniverstaire.createCell(13);
		anne.setCellStyle(style2);
		anne.setCellValue(etablissement.getAnneeUniversitaire());
		
		// tableau 
		for(int r=0;r<rows;r++) {
			XSSFRow row = sheet.createRow(r+12);
			// index
			XSSFCell num = row.createCell(0);
			num.setCellStyle(style);
			num.setCellValue((Integer)r+1);
			//  prenomNomArabe
			XSSFCell prenomNomArabe = row.createCell(1);
			prenomNomArabe.setCellStyle(style);	
			prenomNomArabe.setCellValue((String)enseignants.get(r).getPrenomNomArabe());
			// grade
			XSSFCell grade = row.createCell(2);
			grade.setCellStyle(style);
			System.err.println("titre arabe => "+enseignants.get(r).getGrade());
			grade.setCellValue((String)enseignants.get(r).getGrade().getTitreArabe());
			// الساعات المنجزة
				//  Semster 1
					// cours
			XSSFCell coursS1 = row.createCell(3);
			coursS1.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS1() == null) {
				coursS1.setCellValue((Double)0.0);}
				else {coursS1.setCellValue((Double)enseignants.get(r).getChargeHoraireS1().getCours());}
					// td
			XSSFCell tdS1 = row.createCell(4);
			tdS1.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS1()== null) {
				tdS1.setCellValue((Double)0.0);	}
				else {	tdS1.setCellValue((Double)enseignants.get(r).getChargeHoraireS1().getTd());}
					// tp
			XSSFCell tpS1 = row.createCell(5);
			tpS1.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS1() == null) {
				tpS1.setCellValue((Double)0.0);}
				else {tpS1.setCellValue((Double)enseignants.get(r).getChargeHoraireS1().getTp());}
				
				// semstre 2
					// cours 
			XSSFCell coursS2 = row.createCell(6);
			coursS2.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS2() == null) {
				coursS2.setCellValue((Double)0.0);}
				else {coursS2.setCellValue((Double)enseignants.get(r).getChargeHoraireS2().getCours() );}
					// td
			XSSFCell tdS2 = row.createCell(7);
			tdS2.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS2() == null) {
				tdS2.setCellValue((Double)0.0);}
				else {tdS2.setCellValue((Double)enseignants.get(r).getChargeHoraireS2().getTd());}
					// tp
			XSSFCell tpS2 = row.createCell(8);
			tpS2.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS2() == null) {
				tpS2.setCellValue((Double)0.0);}
				else {tpS2.setCellValue((Double)enseignants.get(r).getChargeHoraireS2().getTp());}
				// stage PFE 
					// nombre de stage pfe par semstre 
			XSSFCell nbPFES = row.createCell(9);
			nbPFES.setCellStyle(style);
			if(enseignants.get(r).getChargeHorairePFE() == null) {
				nbPFES.setCellValue((Integer)0);}
				else {nbPFES.setCellValue((Integer)enseignants.get(r).getChargeHorairePFE().getNombre());}
					// nombre de stage pfe annuaire
			XSSFCell nbPFEA = row.createCell(10);
			nbPFEA.setCellStyle(style);
			if(enseignants.get(r).getChargeHorairePFE() == null) {
				nbPFEA.setCellValue((Double)0.0);}
			else {nbPFEA.setCellValue(enseignants.get(r).getChargeHorairePFE().getCoefficient() * enseignants.get(r).getChargeHorairePFE().getNombre() );}
			// Le nombre total d'heures effectuées au cours de l'année universitaire
				//cours
			XSSFCell coursTotal = row.createCell(11);
			coursTotal.setCellStyle(style);
			double totalCours =0;
			if(enseignants.get(r).getChargeHoraireS1() == null || enseignants.get(r).getChargeHoraireS2() == null ) {
				coursTotal.setCellValue((Double)0.0);}
				else { 
					 totalCours = (enseignants.get(r).getChargeHoraireS1().getCours() + enseignants.get(r).getChargeHoraireS2().getCours())/2;
					coursTotal.setCellValue(totalCours);}
				// td
			XSSFCell tdTotal = row.createCell(12);
			tdTotal.setCellStyle(style);
			double totalTd = 0;
			if(enseignants.get(r).getChargeHoraireS1() == null || enseignants.get(r).getChargeHoraireS2() == null ) {
				tdTotal.setCellValue((Double)0.0);}
				else { 
					totalTd = (enseignants.get(r).getChargeHoraireS1().getTd() + enseignants.get(r).getChargeHoraireS2().getTd())/2;
					tdTotal.setCellValue(totalTd);}
				// tp
			XSSFCell tpTotal = row.createCell(13);
			tpTotal.setCellStyle(style);
			double  totalTp = 0;
			if(enseignants.get(r).getChargeHoraireS1() == null || enseignants.get(r).getChargeHoraireS2() == null ) {
				tpTotal.setCellValue((Double)0.0);}
				else { 
					totalTp = (enseignants.get(r).getChargeHoraireS1().getTp() + enseignants.get(r).getChargeHoraireS2().getTp())/2;
					tpTotal.setCellValue(totalTp);}
				// nombre total des heures sup
			XSSFCell nbHeursTotal = row.createCell(14);
			nbHeursTotal.setCellStyle(style);
			double totalHeurs = (totalCours + totalTd + totalTp);
			if(enseignants.get(r).getChargeHoraireS1() == null || enseignants.get(r).getChargeHoraireS2() == null ) {
				nbHeursTotal.setCellValue((Double)0.0);}
				else { nbHeursTotal.setCellValue(totalHeurs);}
			// Pondiration des heures supp
				// pondiration Cours
			XSSFCell coursP = row.createCell(15);
			coursP.setCellStyle(style);
			double coursP2=0;
			if(enseignants.get(r).getHeuresSupp() == null) {
				coursP.setCellValue((Double)0.0);}
				else {coursP.setCellValue(enseignants.get(r).getHeuresSupp().getCours());
				coursP2=enseignants.get(r).getHeuresSupp().getCours();}
				// pondiration Td
			XSSFCell tdP = row.createCell(16);
			tdP.setCellStyle(style);
			double tdP2=0;
			if(enseignants.get(r).getHeuresSupp() == null) {
				tdP.setCellValue((Double)0.0);}
				else { tdP.setCellValue(enseignants.get(r).getHeuresSupp().getTd());
				tdP2= enseignants.get(r).getHeuresSupp().getTd();}
				// Pondiration Tp
			XSSFCell tpP = row.createCell(17);
			tpP.setCellStyle(style);
			double tpP2=0;
			if(enseignants.get(r).getHeuresSupp() == null) {
				tpP.setCellValue((Double)0.0);}
				else { tpP.setCellValue(enseignants.get(r).getHeuresSupp().getTp());
				tpP2= enseignants.get(r).getHeuresSupp().getTp();}
			// Rest nombre heurs PFE annuaire 
			XSSFCell restPfe = row.createCell(18);
			restPfe.setCellStyle(style);
			if(enseignants.get(r).getChargeHoraireS1() != null && enseignants.get(r).getChargeHoraireS2() != null && enseignants.get(r).getChargeHorairePFE() != null ) {
				if(totalHeurs > enseignants.get(r).getGrade().getDue()) {
					restPfe.setCellValue(enseignants.get(r).getChargeHorairePFE().getNombre());
				}else if((totalHeurs + enseignants.get(r).getChargeHorairePFE().getNombre())> enseignants.get(r).getGrade().getDue()) {
					restPfe.setCellValue((enseignants.get(r).getChargeHorairePFE().getNombre() + totalHeurs)- enseignants.get(r).getGrade().getDue());}
				else { restPfe.setCellValue(0);}
			}else { restPfe.setCellValue(0);}
			
			//  !!!! sa3at al mora5as fiha 
				// cours
			XSSFCell coursIllegale = row.createCell(22);
			coursIllegale.setCellStyle(style);
				// Td
			XSSFCell tdIllegale = row.createCell(23);
			tdIllegale.setCellStyle(style);
				// Tp
			XSSFCell tpIllegale = row.createCell(24);
			tpIllegale.setCellStyle(style);
			// sa3at al mora5as fiha
				// Cours
			XSSFCell coursLegale = row.createCell(19);
			coursLegale.setCellStyle(style);
				// Td
			XSSFCell tdLegale = row.createCell(20);
			tdLegale.setCellStyle(style);
				// Tp
			XSSFCell tpLegale =row.createCell(21);
			tpLegale.setCellStyle(style);
			
			//  !!!! sa3at al mora5as fiha 
		
			if(totalHeurs> (enseignants.get(r).getGrade().getDue()+enseignants.get(r).getGrade().getMaxDue())) {
			System.err.println("totlHeurs :" +totalHeurs );
			System.err.println("test Due : "+ (enseignants.get(r).getGrade().getDue()+enseignants.get(r).getGrade().getMaxDue()));
				coursIllegale.setCellValue(coursP2);
				tdIllegale.setCellValue(tdP2);
				tpIllegale.setCellValue(tpP2);
			//   sa3at al mora5as fiha 
			}else if(totalHeurs> enseignants.get(r).getGrade().getDue()) {
				
				coursLegale.setCellValue(coursP2);
				tdLegale.setCellValue(tdP2);
				tpLegale.setCellValue(tpP2);
			}
		
			// 9arar sayed il wazid just style wyab9a fera8
			XSSFCell decisionMinistre =row.createCell(25);
			decisionMinistre.setCellStyle(style);
		}
		fis.close();
		FileOutputStream fos=new FileOutputStream("src/main/resources/heuresSup2.xlsx");
		workbook.write(fos);
		workbook.removeSheetAt(0);
		fos.close();
		
	}

	@Override
	public void deleteFile(String fileName) {
		// TODO Auto-generated method stub
		
		File file = new File("src/main/resources/"+fileName);
		file.delete();
		
	}

	@Override
	public void convertToPDF(String fileName) throws Exception   {
	
	
		
		com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook("src/main/resources/"+fileName);
	   
		PdfSaveOptions saveOptions = new PdfSaveOptions(SaveFormat.PDF);
		saveOptions.setAllColumnsInOnePagePerSheet(true);

		workbook.save("src/main/resources/"+fileName.subSequence(0, fileName.length()-5)+".pdf", saveOptions);
		
	
	}

	@Override
	public Enseignant initialiserChargeHoraire(Enseignant enseignant) {
		if(enseignant.getChargeHorairePFE() == null) {
			HeurePFE heurePFE = new HeurePFE();
					heurePFE.setNombre(0);
					enseignant.setChargeHorairePFE(heurePFE);
		}
		if (enseignant.getChargeHoraireS1() == null) {
			ChargeHoraireEffectif chargeHoraireEffectif1 = new ChargeHoraireEffectif();
			chargeHoraireEffectif1.setCours(0);
			chargeHoraireEffectif1.setTd(0);
			chargeHoraireEffectif1.setTp(0);
			enseignant.setChargeHoraireS1(chargeHoraireEffectif1);
		}
		if(enseignant.getChargeHoraireS2() == null) {
			ChargeHoraireEffectif chargeHoraireEffectif2 = new ChargeHoraireEffectif();
			chargeHoraireEffectif2.setCours(0);
			chargeHoraireEffectif2.setTd(0);
			chargeHoraireEffectif2.setTp(0);
			enseignant.setChargeHoraireS2(chargeHoraireEffectif2);
		}
		return enseignant;
	}

	@Override
	public void writeFileMemoire(Enseignant enseignant) throws IOException {
		String filePath="src/main/resources/Memoire.xlsx";
		FileInputStream fis=new FileInputStream(filePath);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheetAt(0);
		Etablissement etablissement = new Etablissement();
		etablissement = etablissementService.findEtablissement();
		// anneeUniverstaire
		XSSFRow anneeUniverstaire = sheet.getRow(1);
		XSSFCell annee = anneeUniverstaire.getCell(10);
		annee.setCellValue(etablissement.getAnneeUniversitaire());
		// period d'enseignement en arabe 
		XSSFRow periodArabe = sheet.getRow(7);
		XSSFCell period = periodArabe.getCell(5);
		period.setCellValue(etablissement.getPeriodeArabe());
		// pernom et nom
		XSSFRow prenomNomArabe = sheet.getRow(8);
		XSSFCell prenomNom = prenomNomArabe.getCell(1);
		prenomNom.setCellValue(enseignant.getPrenomNomArabe());
		// grade en arabe
		XSSFRow gradeArabe =sheet.getRow(8);
		XSSFCell grade = gradeArabe.getCell(5);
		grade.setCellValue(enseignant.getGrade().getTitreArabe());
		// identidiant unique 
		XSSFRow identifiantUnique =sheet.getRow(9);
		XSSFCell identifiant = identifiantUnique.getCell(5);
		identifiant.setCellValue(enseignant.getIdentifiantUnique());
		// cin 
		XSSFRow cinRow = sheet.getRow(10);
		XSSFCell cin = cinRow.getCell(5);
		cin.setCellValue(enseignant.getCin());
		
		// Row de Cours 
		XSSFRow coursRow = sheet.getRow(14);
			//Nombre d'heures
		XSSFCell nbheureCours = coursRow.getCell(2);
		nbheureCours.setCellValue(enseignant.getHeuresSupp().getCours());
			// Pourcentage annuel	
		XSSFCell pAnnuelCours = coursRow.getCell(3);
		pAnnuelCours.setCellValue(enseignant.getGrade().getSalaireCours());
			// Pourcentage annuel brut
		XSSFCell pAnnuelBrut = coursRow.getCell(4);
		double pAnnuelBrut2 = Precision.round( enseignant.getHeuresSupp().getCours() * enseignant.getGrade().getSalaireCours(),3);
		pAnnuelBrut.setCellValue(pAnnuelBrut2);
			// Absences
		XSSFCell absencesCours = coursRow.getCell(5);
		double absencesCours2= Precision.round( (enseignant.getAbsence() / 3),3);
		absencesCours.setCellValue(absencesCours2);
			// Taux horaire
		XSSFCell tauxHoraireCours = coursRow.getCell(6);
		double tauxHoraireCours2 =Precision.round((enseignant.getGrade().getSalaireCours() / etablissement.getNombreSomaine()),3);
		tauxHoraireCours.setCellValue(tauxHoraireCours2);
			// Montant de la réduction 
		XSSFCell montantReductionCours = coursRow.getCell(7);
		double montantReductionCours2 = Precision.round((tauxHoraireCours2 * absencesCours2),3);
		montantReductionCours.setCellValue(montantReductionCours2);
			// Montant Payable
		XSSFCell montantPayableCours = coursRow.getCell(8);
		double montantPayableCours2 = Precision.round((pAnnuelBrut2 - montantReductionCours2),3);
		montantPayableCours.setCellValue(montantPayableCours2);
			// Impôts Revenu
		XSSFCell impotsRevenuCours = coursRow.getCell(9);
		double impotsRevenuCours2 = Precision.round((montantPayableCours2 * 0.15),3);
		impotsRevenuCours.setCellValue(impotsRevenuCours2);
			//Montant net
		XSSFCell montantNetCours = coursRow.getCell(10);
		double montantNetCours2 =  Precision.round((montantPayableCours2 - impotsRevenuCours2),3);
		montantNetCours.setCellValue(montantNetCours2);
		
		// Row de Td
		XSSFRow tdRow = sheet.getRow(15);
			//Nombre d'heures
		XSSFCell nbheureTd = tdRow.getCell(2);
		nbheureTd.setCellValue(enseignant.getHeuresSupp().getTd());
			// Pourcentage annuel
		XSSFCell pAnnuelTd = tdRow.getCell(3);
		pAnnuelTd.setCellValue(enseignant.getGrade().getSalaireTd());
			// Pourcentage annuel brut
		XSSFCell pAnnuelBrutTd = tdRow.getCell(4);
		double pAnnuelBrutTd2 = Precision.round( enseignant.getHeuresSupp().getTd() * enseignant.getGrade().getSalaireTd(),3);
		pAnnuelBrutTd.setCellValue(pAnnuelBrutTd2);
			// Absences
		XSSFCell absencesTd = tdRow.getCell(5);
		double absencesTd2 = Precision.round( (enseignant.getAbsence() / 3),3);
		absencesTd.setCellValue(absencesTd2);
			// Taux horaire
		XSSFCell tauxHoraireTd = tdRow.getCell(6);
		double tauxHoraireTd2 = Precision.round((enseignant.getGrade().getSalaireTd() / etablissement.getNombreSomaine()),3);
		tauxHoraireTd.setCellValue(tauxHoraireTd2);
			// Montant de la réduction 
		XSSFCell montantReductionTd = tdRow.getCell(7);
		double montantReductionTd2 =  Precision.round((tauxHoraireTd2 * absencesTd2),3);
		montantReductionTd.setCellValue(montantReductionTd2);
			// Montant Payable
		XSSFCell montantPayableTd = tdRow.getCell(8);
		double montantPayableTd2 = Precision.round((pAnnuelBrutTd2 - montantReductionTd2),3);
		montantPayableTd.setCellValue(montantPayableTd2);
			// Impôts Revenu
		XSSFCell impotsRevenuTd = tdRow.getCell(9);
		double impotsRevenuTd2 = Precision.round((montantPayableTd2 * 0.15),3);
		impotsRevenuTd.setCellValue(impotsRevenuTd2);
			// Montant net
		XSSFCell montantNetTd = tdRow.getCell(10);
		double montantNetTd2 = Precision.round((montantPayableTd2 - impotsRevenuTd2),3);
		montantNetTd.setCellValue(montantNetTd2);
		
		// Row de Tp 
		XSSFRow tpRow = sheet.getRow(16);
			// Nombre d'heures
		XSSFCell nbheureTp = tpRow.getCell(2);
		nbheureTp.setCellValue(enseignant.getHeuresSupp().getTp());
			// Pourcentage annuel
		XSSFCell pAnnuelTp = tpRow.getCell(3);
		pAnnuelTp.setCellValue(enseignant.getGrade().getSalaireTp());
			// Pourcentage annuel brut
		XSSFCell pAnnuelBrutTp = tpRow.getCell(4);
		double pAnnuelBrutTp2 = Precision.round( enseignant.getHeuresSupp().getTp() * enseignant.getGrade().getSalaireTp(),3);
		pAnnuelBrutTp.setCellValue(pAnnuelBrutTp2);
			// Absences
		XSSFCell absencesTp = tpRow.getCell(5);
		double absencesTp2 = Precision.round( (enseignant.getAbsence() / 3),3);
		absencesTp.setCellValue(absencesTp2);
			// Taux horaire
		XSSFCell tauxHoraireTp = tpRow.getCell(6);
		double tauxHoraireTp2 = Precision.round((enseignant.getGrade().getSalaireTp() / etablissement.getNombreSomaine()),3);
		tauxHoraireTp.setCellValue(tauxHoraireTp2);
			// Montant de la réduction 
		XSSFCell montantReductionTp = tpRow.getCell(7);
		double montantReductionTp2 =  Precision.round((tauxHoraireTp2 * absencesTp2),3);
		montantReductionTp.setCellValue(montantReductionTp2);
			// Montant Payable
		XSSFCell montantPayableTp = tpRow.getCell(8);
		double montantPayableTp2 = Precision.round((pAnnuelBrutTp2 - montantReductionTp2),3);
		montantPayableTp.setCellValue(montantPayableTp2);
			// Impôts Revenu
		XSSFCell impotsRevenuTp = tpRow.getCell(9);
		double impotsRevenuTp2 = Precision.round((montantPayableTp2 * 0.15),3);
		impotsRevenuTp.setCellValue(impotsRevenuTp2);
			// Montant net
		XSSFCell montantNetTp = tpRow.getCell(10);
		double montantNetTp2 = Precision.round((montantPayableTp2 - impotsRevenuTp2),3);
		montantNetTp.setCellValue(montantNetTp2);
		
		// Row de total 
		XSSFRow totalRow = sheet.getRow(17);
			// Total Pourcentage annuel brut
		XSSFCell totalPAnnuelBrut = totalRow.getCell(4);
		double totalPAnnuelBrut2 = Precision.round((pAnnuelBrut2 + pAnnuelBrutTd2 + pAnnuelBrutTp2),3);
		totalPAnnuelBrut.setCellValue(totalPAnnuelBrut2);
			// Total Montant de la réduction 
		XSSFCell totalMontantReduction = totalRow.getCell(7);
		double totalMontantReduction2 = Precision.round((montantReductionCours2 + montantReductionTd2 + montantReductionTp2),3);
		totalMontantReduction.setCellValue(totalMontantReduction2);
			// Montant Payable
		XSSFCell totalMontantPayabl = totalRow.getCell(8);
		double totalMontantPayabl2 = Precision.round((montantPayableCours2 + montantPayableTp2 + montantPayableTd2 ),3);
		totalMontantPayabl.setCellValue(totalMontantPayabl2);
			// Impôts Revenu
		XSSFCell totalImpotsRevenu = totalRow.getCell(9);
		double totalImpotsRevenu2 = Precision.round((impotsRevenuTp2 + impotsRevenuCours2 + impotsRevenuTd2),3);
		totalImpotsRevenu.setCellValue(totalImpotsRevenu2);
			// Montant net
		XSSFCell totalMontantNet = totalRow.getCell(10);
		double totalMontantNet2 = Precision.round((montantNetTp2 + montantNetCours2 + montantNetTd2),3); 
		totalMontantNet.setCellValue(totalMontantNet2);
		System.err.println(totalMontantNet2);
		System.err.println("enseignant => "+ enseignant);
		// Rib 
		XSSFRow Rib = sheet.getRow(19);
		XSSFCell rib = Rib.getCell(2);
		rib.setCellValue(enseignant.getRib());
		
		// Montant Final 
		XSSFRow Montant = sheet.getRow(20);
		XSSFCell montant = Montant.getCell(7);
		montant.setCellValue(totalMontantNet2);
		
		fis.close();
		FileOutputStream fos=new FileOutputStream("src/main/resources/Memoire2.xlsx");
		workbook.write(fos);
		fos.close();
	
		
	}

	@Override
	public List<Enseignant> findByCodeCouleurWithJPQL() {
		List<Enseignant> enseignants = enseignantRespository.findByCodeCouleurWithJPQL();
		List<Enseignant> enseignants2 = new ArrayList<Enseignant>();
		for(Enseignant e: enseignants) {
			if(e.getHeuresSupp().getTd()>0) {
				enseignants2.add(e);
			}
		}
		return enseignants2;
	}

	@Override
	public void writeFileRecap() throws Exception {
		String filePath="src/main/resources/Recap.xlsx";
		FileInputStream fis=new FileInputStream(filePath);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheetAt(0);
		CellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		List<Enseignant> enseignants = findByCodeCouleurWithJPQL();
	
		
		double MontantPayableTab1 = 0;
		double MontantPayableCompteBancaireTab1 = 0;
		double MontantPayableComptePostalTab1 = 0;
		for(int r= 0; r<enseignants.size();r++) {
			XSSFRow row = sheet.createRow(r+6);
			// Prénom et Nom
			XSSFCell prenomNom = row.createCell(0);
			prenomNom.setCellStyle(style);
			prenomNom.setCellValue(enseignants.get(r).getPrenomNomArabe());
			// Grade
			XSSFCell grade = row.createCell(1);
			grade.setCellStyle(style);
			grade.setCellValue(enseignants.get(r).getGrade().getTitreArabe());
			// RIB 
			XSSFCell rib = row.createCell(2);
			rib.setCellStyle(style);
			rib.setCellValue(String.valueOf(enseignants.get(r).getRib()));
			// Compte Postal Ou Compte Bancaire
			XSSFCell compte = row.createCell(3);
			compte.setCellStyle(style);
			compte.setCellValue(verifRib(enseignants.get(r).getRib()));
			//  montant Payable	
			XSSFCell totalMontantPayable = row.createCell(4);
			totalMontantPayable.setCellStyle(style);
			totalMontantPayable.setCellValue(totalMontantPayable(enseignants.get(r)));
			// Impôts Revenu	
			XSSFCell impotsRevenu = row.createCell(5);
			impotsRevenu.setCellStyle(style);
			impotsRevenu.setCellValue(impotsRevenu(totalMontantPayable(enseignants.get(r))));
			// Montant net
			XSSFCell totalMontantNet = row.createCell(6);
			totalMontantNet.setCellStyle(style);
			totalMontantNet.setCellValue(totalMontantNet(totalMontantPayable(enseignants.get(r)),impotsRevenu(totalMontantPayable(enseignants.get(r))) ) );
									// tableau d'entéte 
			MontantPayableTab1 =MontantPayableTab1 + totalMontantPayable(enseignants.get(r));
			if(verifRib(enseignants.get(r).getRib()).equals("Compte postal")) {
				MontantPayableComptePostalTab1 = MontantPayableComptePostalTab1 + totalMontantPayable(enseignants.get(r));
			}else {
				MontantPayableCompteBancaireTab1 = MontantPayableCompteBancaireTab1 + totalMontantPayable(enseignants.get(r));
			}
			
		}
		//Total compte postal	
		XSSFRow rowTotalComptePostal = sheet.getRow(1);
			//Montant Payable	
		XSSFCell totalMontantPayableComptePostalTab1 = rowTotalComptePostal.getCell(4);
		totalMontantPayableComptePostalTab1.setCellValue(MontantPayableComptePostalTab1);
			// Impôts Revenu	
		XSSFCell  totalImpotsRevenuPostalTab1 = rowTotalComptePostal.getCell(5);
		totalImpotsRevenuPostalTab1.setCellValue(impotsRevenu(MontantPayableComptePostalTab1));
			// Montant Net
		XSSFCell totalMontantNetPostalTab1 = rowTotalComptePostal.getCell(6);
		totalMontantNetPostalTab1.setCellValue(totalMontantNet(MontantPayableComptePostalTab1, impotsRevenu(MontantPayableComptePostalTab1)));
		// Total compte Bancaire 
		XSSFRow rowTotalCompteBancaire = sheet.getRow(2);
			// Montant Payble
		XSSFCell totalMontantPayableCompteBancaireTab1 = rowTotalCompteBancaire.getCell(4);
		totalMontantPayableCompteBancaireTab1.setCellValue(MontantPayableCompteBancaireTab1);
			//  Impôts Revenu
		XSSFCell totalImpotsRevenuBancaireTab1 = rowTotalCompteBancaire.getCell(5);
		totalImpotsRevenuBancaireTab1.setCellValue(impotsRevenu(MontantPayableCompteBancaireTab1));
		// Montant Net
		XSSFCell totalMontantNetBancaireTab1 = rowTotalCompteBancaire.getCell(6);
		totalMontantNetBancaireTab1.setCellValue(totalMontantNet(MontantPayableCompteBancaireTab1, impotsRevenu(MontantPayableCompteBancaireTab1)));
		// Total Compte Bancaire And Postal
		XSSFRow rowTotalCompte = sheet.getRow(3);
			// Montant Payble
		XSSFCell totalMontantPayableTab1 = rowTotalCompte.getCell(4);
		totalMontantPayableTab1.setCellValue(MontantPayableTab1);
			//  Impôts Revenu
		XSSFCell totalImpotsRevenuTab1 = rowTotalCompte.getCell(5);
		totalImpotsRevenuTab1.setCellValue(impotsRevenu(MontantPayableTab1));
			// Montant Net
		XSSFCell totalMontantNetTab1 = rowTotalCompte.getCell(6);
		totalMontantNetTab1.setCellValue(totalMontantNet(MontantPayableTab1, impotsRevenu(MontantPayableTab1)));
		fis.close();
		FileOutputStream fos=new FileOutputStream("src/main/resources/Recap2.xlsx");
		workbook.write(fos);
		workbook.removeSheetAt(0);
		fos.close();
	}
	
	public String verifRib(long rib) {
		String ch = String.valueOf(rib);
		if(ch.substring(0, 2).equals("17")) {
			return "Compte postal";
		}else {
			return "Compte bancaire";
		}
	}
	public double totalMontantPayable(Enseignant e) {
		//   Precision.round( ,3);
		Etablissement etablissement = etablissementService.findEtablissement();
		double absence = Precision.round(e.getAbsence()/3,3); 
		double cousPourcentageAnnuelBrut = Precision.round(e.getHeuresSupp().getCours() * e.getGrade().getSalaireCours() ,3);
		double tdPourcentageAnnuelBrut = Precision.round(e.getHeuresSupp().getTd() * e.getGrade().getSalaireTd() ,3);
		double tpPourcentageAnnuelBrut =  Precision.round(e.getHeuresSupp().getTp() * e.getGrade().getSalaireTp() ,3);
		double coursTauxHoraire = Precision.round(e.getGrade().getSalaireCours() / etablissement.getNombreSomaine() ,3);
		double tdTauxHoraire = Precision.round(e.getGrade().getSalaireTd() / etablissement.getNombreSomaine() ,3);
		double tpTauxHoraire = Precision.round(e.getGrade().getSalaireTp() / etablissement.getNombreSomaine() ,3);
		double coursMontantReduction = Precision.round(coursTauxHoraire * absence ,3);
		double tdMontantReduction = Precision.round(tdTauxHoraire * absence ,3);
		double tpMontantReduction = Precision.round(tpTauxHoraire * absence ,3);
		double coursMontantPayable = Precision.round(cousPourcentageAnnuelBrut - coursMontantReduction ,3);
		double tdMontantPayable = Precision.round(tdPourcentageAnnuelBrut - tdMontantReduction ,3);
		double tpMontantPayable = Precision.round(tpPourcentageAnnuelBrut - tpMontantReduction ,3);
		return Precision.round(coursMontantPayable + tdMontantPayable + tpMontantPayable ,3);
		
	}
	public double impotsRevenu(double totalMontantPayable) {
		return Precision.round(totalMontantPayable * 0.15 ,3);
	}

	public double totalMontantNet(double totalMontantPayable, double impotsRevenu) {
		return Precision.round(totalMontantPayable - impotsRevenu ,3);
	}

	@Override
	public void writeFilelistMemoire() throws Exception {
		// concatner deux colone (firstrow, lastrow, firstcol, lastcol)
				//sheet.addMergedRegion(new CellRangeAddress(3,3,20,21));
		String filePath="src/main/resources/TableauRecap.xlsx";
		FileInputStream fis=new FileInputStream(filePath);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheetAt(0);
		
		///   background Total 
		XSSFFont headerFont = workbook.createFont();
		//headerFont.setColor(IndexedColors.WHITE.index);
		 CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
		 headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		 headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		 headerCellStyle.setBorderTop(BorderStyle.THIN);
		 headerCellStyle.setBorderLeft(BorderStyle.THIN);
		 headerCellStyle.setBorderBottom(BorderStyle.THIN);
		 headerCellStyle.setBorderRight(BorderStyle.THIN);
		 headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		  headerCellStyle.setFont(headerFont);
		 
		CellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		CellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		List<Enseignant> enseignants = findByCodeCouleurWithJPQL();
		Etablissement etablissement = etablissementService.findEtablissement();
		int i = -3;
		for(int r=0; r<enseignants.size();r++) {
			i=i+3;
			
			// Row de Cours 
			XSSFRow coursRow = sheet.createRow(r+3+i);
			// prenom et nom arabe
			XSSFCell prenomNom = coursRow.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(r+3+i,5+r+i,0,0));
			setStyleCellMerged(r+3+i,5+r+i,0,0,sheet);
			prenomNom.setCellStyle(style2);
			prenomNom.setCellValue(enseignants.get(r).getPrenomNomArabe());
			// Grade
			XSSFCell grade = coursRow.createCell(1);
			sheet.addMergedRegion(new CellRangeAddress(r+3+i,5+r+i,1,1));
			setStyleCellMerged(r+3+i,5+r+i,1,1,sheet);
			grade.setCellStyle(style2);
			grade.setCellValue(enseignants.get(r).getGrade().getTitre());
			//  RIB
			XSSFCell rib = coursRow.createCell(2);
			sheet.addMergedRegion(new CellRangeAddress(r+3+i,5+r+i,2,2));
			setStyleCellMerged(r+3+i,5+r+i,2,2,sheet);
			rib.setCellStyle(style2);
			rib.setCellValue(String.valueOf(enseignants.get(r).getRib()));
			// type d'enseignement
			XSSFCell typeenseignementCours = coursRow.createCell(3);
			typeenseignementCours.setCellStyle(style);
			typeenseignementCours.setCellValue("د.أساسية");
			
			// Nombre d'heures
			XSSFCell nbheureCours = coursRow.createCell(4);
			nbheureCours.setCellStyle(style);
			nbheureCours.setCellValue(enseignants.get(r).getHeuresSupp().getCours());
			// Pourcentage annuel	
			XSSFCell pAnnuelCours = coursRow.createCell(5);
			pAnnuelCours.setCellStyle(style);
			pAnnuelCours.setCellValue(enseignants.get(r).getGrade().getSalaireCours());
			// Pourcentage annuel brut
			XSSFCell pAnnuelBrut = coursRow.createCell(6);
			pAnnuelBrut.setCellStyle(style);
			double pAnnuelBrut2 = Precision.round( enseignants.get(r).getHeuresSupp().getCours() * enseignants.get(r).getGrade().getSalaireCours(),3);
			pAnnuelBrut.setCellValue(pAnnuelBrut2);
			//		Absences
			XSSFCell absencesCours = coursRow.createCell(7);
			absencesCours.setCellStyle(style);
			double absencesCours2= Precision.round( (enseignants.get(r).getAbsence() / 3),3);
			absencesCours.setCellValue(absencesCours2);
			//Taux horaire
			XSSFCell tauxHoraireCours = coursRow.createCell(8);
			tauxHoraireCours.setCellStyle(style);
			double tauxHoraireCours2 =Precision.round((enseignants.get(r).getGrade().getSalaireCours() / etablissement.getNombreSomaine()),3);
			tauxHoraireCours.setCellValue(tauxHoraireCours2);
			// Montant de la réduction 
			XSSFCell montantReductionCours = coursRow.createCell(9);
			montantReductionCours.setCellStyle(style);
			double montantReductionCours2 = Precision.round((tauxHoraireCours2 * absencesCours2),3);
			montantReductionCours.setCellValue(montantReductionCours2);
			// Montant Payable
			XSSFCell montantPayableCours = coursRow.createCell(10);
			montantPayableCours.setCellStyle(style);
			double montantPayableCours2 = Precision.round((pAnnuelBrut2 - montantReductionCours2),3);
			montantPayableCours.setCellValue(montantPayableCours2);
			// Impôts Revenu
			XSSFCell impotsRevenuCours = coursRow.createCell(11);
			impotsRevenuCours.setCellStyle(style);
			double impotsRevenuCours2 = Precision.round((montantPayableCours2 * 0.15),3);
			impotsRevenuCours.setCellValue(impotsRevenuCours2);
			// Montant net
			XSSFCell montantNetCours = coursRow.createCell(12);
			montantNetCours.setCellStyle(style);
			double montantNetCours2 =  Precision.round((montantPayableCours2 - impotsRevenuCours2),3);
			montantNetCours.setCellValue(montantNetCours2);
			
			// Row de TD 
			XSSFRow tdRow = sheet.getRow(r+4+i);
			// type d'enseignement
						XSSFCell typeenseignementTd = tdRow.createCell(3);
						typeenseignementTd.setCellStyle(style);
						typeenseignementTd.setCellValue("د.مسيرة");
				//Nombre d'heures
			XSSFCell nbheureTd = tdRow.createCell(4);
			nbheureTd.setCellStyle(style);
			nbheureTd.setCellValue(enseignants.get(r).getHeuresSupp().getTd());
				//Pourcentage annuel
			XSSFCell pAnnuelTd = tdRow.createCell(5);
			pAnnuelTd.setCellStyle(style);
			pAnnuelTd.setCellValue(enseignants.get(r).getGrade().getSalaireTd());
				// Pourcentage annuel brut
			XSSFCell pAnnuelBrutTd = tdRow.createCell(6);
			pAnnuelBrutTd.setCellStyle(style);
			double pAnnuelBrutTd2 = Precision.round( enseignants.get(r).getHeuresSupp().getTd() * enseignants.get(r).getGrade().getSalaireTd(),3);
			pAnnuelBrutTd.setCellValue(pAnnuelBrutTd2);
				// Absences
			XSSFCell absencesTd = tdRow.createCell(7);
			absencesTd.setCellStyle(style);
			double absencesTd2 = Precision.round( (enseignants.get(r).getAbsence() / 3),3);
			absencesTd.setCellValue(absencesTd2);
				// Taux horaire
			XSSFCell tauxHoraireTd = tdRow.createCell(8);
			tauxHoraireTd.setCellStyle(style);
			double tauxHoraireTd2 = Precision.round((enseignants.get(r).getGrade().getSalaireTd() / etablissement.getNombreSomaine()),3);
			tauxHoraireTd.setCellValue(tauxHoraireTd2);
				// Montant de la réduction 
			XSSFCell montantReductionTd = tdRow.createCell(9);
			montantReductionTd.setCellStyle(style);
			double montantReductionTd2 =  Precision.round((tauxHoraireTd2 * absencesTd2),3);
			montantReductionTd.setCellValue(montantReductionTd2);
				// Montant Payable
			XSSFCell montantPayableTd = tdRow.createCell(10);
			montantPayableTd.setCellStyle(style);
			double montantPayableTd2 = Precision.round((pAnnuelBrutTd2 - montantReductionTd2),3);
			montantPayableTd.setCellValue(montantPayableTd2);
				// Impôts Revenu
			XSSFCell impotsRevenuTd = tdRow.createCell(11);
			impotsRevenuTd.setCellStyle(style);
			double impotsRevenuTd2 = Precision.round((montantPayableTd2 * 0.15),3);
			impotsRevenuTd.setCellValue(impotsRevenuTd2);
				// Montant net
			XSSFCell montantNetTd = tdRow.createCell(12);
			montantNetTd.setCellStyle(style);
			double montantNetTd2 = Precision.round((montantPayableTd2 - impotsRevenuTd2),3);
			montantNetTd.setCellValue(montantNetTd2);
			
			// Row de Tp 
			XSSFRow tpRow = sheet.getRow(r+5+i);
				// type d'enseignement
			XSSFCell typeenseignementTp = tpRow.createCell(3);
			typeenseignementTp.setCellStyle(style);
			typeenseignementTp.setCellValue("د.تطبيقية");
				// Nombre d'heures
			XSSFCell nbheureTp = tpRow.createCell(4);
			nbheureTp.setCellStyle(style);
			nbheureTp.setCellValue(enseignants.get(r).getHeuresSupp().getTp());
				// Pourcentage annuel
			XSSFCell pAnnuelTp = tpRow.createCell(5);
			pAnnuelTp.setCellStyle(style);
			pAnnuelTp.setCellValue(enseignants.get(r).getGrade().getSalaireTp());
				// Pourcentage annuel brut
			XSSFCell pAnnuelBrutTp = tpRow.createCell(6);
			pAnnuelBrutTp.setCellStyle(style);
			double pAnnuelBrutTp2 = Precision.round( enseignants.get(r).getHeuresSupp().getTp() * enseignants.get(r).getGrade().getSalaireTp(),3);
			pAnnuelBrutTp.setCellValue(pAnnuelBrutTp2);
				// Absences
			XSSFCell absencesTp = tpRow.createCell(7);
			absencesTp.setCellStyle(style);
			double absencesTp2 = Precision.round( (enseignants.get(r).getAbsence() / 3),3);
			absencesTp.setCellValue(absencesTp2);
				// Taux horaire
			XSSFCell tauxHoraireTp = tpRow.createCell(8);
			tauxHoraireTp.setCellStyle(style);
			double tauxHoraireTp2 = Precision.round((enseignants.get(r).getGrade().getSalaireTp() / etablissement.getNombreSomaine()),3);
			tauxHoraireTp.setCellValue(tauxHoraireTp2);
				// Montant de la réduction
			XSSFCell montantReductionTp = tpRow.createCell(9);
			montantReductionTp.setCellStyle(style);
			double montantReductionTp2 =  Precision.round((tauxHoraireTp2 * absencesTp2),3);
			montantReductionTp.setCellValue(montantReductionTp2);
				// Montant Payable
			XSSFCell montantPayableTp = tpRow.createCell(10);
			montantPayableTp.setCellStyle(style);
			double montantPayableTp2 = Precision.round((pAnnuelBrutTp2 - montantReductionTp2),3);
			montantPayableTp.setCellValue(montantPayableTp2);
				// Impôts Revenu
			XSSFCell impotsRevenuTp = tpRow.createCell(11);
			impotsRevenuTp.setCellStyle(style);
			double impotsRevenuTp2 = Precision.round((montantPayableTp2 * 0.15),3);
			impotsRevenuTp.setCellValue(impotsRevenuTp2);
				// Montant net
			XSSFCell montantNetTp = tpRow.createCell(12);
			montantNetTp.setCellStyle(style);
			double montantNetTp2 = Precision.round((montantPayableTp2 - impotsRevenuTp2),3);
			montantNetTp.setCellValue(montantNetTp2);
			// Row de total 
			XSSFRow totalRow = sheet.createRow(r+6+i);
				// total
			XSSFCell Total = totalRow.createCell(5);
			Total.setCellStyle(headerCellStyle);
			Total.setCellValue("المجموع");
				// Total Pourcentage annuel brut
			XSSFCell totalPAnnuelBrut = totalRow.createCell(6);
			totalPAnnuelBrut.setCellStyle(headerCellStyle);
			double totalPAnnuelBrut2 = Precision.round((pAnnuelBrut2 + pAnnuelBrutTd2 + pAnnuelBrutTp2),3);
			totalPAnnuelBrut.setCellValue(totalPAnnuelBrut2);
				// Total Montant de la réduction 
			XSSFCell totalMontantReduction = totalRow.createCell(9);
			totalMontantReduction.setCellStyle(headerCellStyle);
			double totalMontantReduction2 = Precision.round((montantReductionCours2 + montantReductionTd2 + montantReductionTp2),3);
			totalMontantReduction.setCellValue(totalMontantReduction2);
				// Montant Payable
			XSSFCell totalMontantPayabl = totalRow.createCell(10);
			totalMontantPayabl.setCellStyle(headerCellStyle);
			double totalMontantPayabl2 = Precision.round((montantPayableCours2 + montantPayableTp2 + montantPayableTd2 ),3);
			totalMontantPayabl.setCellValue(totalMontantPayabl2);
				// Impôts Revenu
			XSSFCell totalImpotsRevenu = totalRow.createCell(11);
			totalImpotsRevenu.setCellStyle(headerCellStyle);
			double totalImpotsRevenu2 = Precision.round((impotsRevenuTp2 + impotsRevenuCours2 + impotsRevenuTd2),3);
			totalImpotsRevenu.setCellValue(totalImpotsRevenu2);
				// Montant net
			XSSFCell totalMontantNet = totalRow.createCell(12);
			totalMontantNet.setCellStyle(headerCellStyle);
			double totalMontantNet2 = Precision.round((montantNetTp2 + montantNetCours2 + montantNetTd2),3); 
			totalMontantNet.setCellValue(totalMontantNet2);
			
			
			
			
		}

		fis.close();
		FileOutputStream fos=new FileOutputStream("src/main/resources/TableauRecap2.xlsx");
		workbook.write(fos);
		fos.close();
	}
	
	public void setStyleCellMerged(int firstRow, int lastRow, int firstCell, int lastCell, XSSFSheet sheet){
		RegionUtil.setBorderLeft(BorderStyle.THIN,new CellRangeAddress(firstRow,lastRow,firstCell,lastCell),sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN,new CellRangeAddress(firstRow,lastRow,firstCell,lastCell), sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN,new CellRangeAddress(firstRow,lastRow,firstCell,lastCell),sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN,new CellRangeAddress(firstRow,lastRow,firstCell,lastCell),sheet);
	}

	@Override
	public void writeFileDossierEnseignant(Enseignant enseignant) throws IOException {
		String filePath="src/main/resources/DossierEnseignant.xlsx";
		FileInputStream fis=new FileInputStream(filePath);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheetAt(0);
		Etablissement etablissement = etablissementService.findEtablissement();
		// AnneeUniverstaire 
		XSSFRow anneeUniverstaire = sheet.getRow(10);
		XSSFCell annee = anneeUniverstaire.getCell(1);
		annee.setCellValue(etablissement.getAnneeUniversitaire());
		// prenom et Nom arabe
		XSSFRow prenomNomArabe = sheet.getRow(12);
		XSSFCell prenomNom = prenomNomArabe.getCell(1);
		prenomNom.setCellValue(enseignant.getPrenomNomArabe());
		// grade 
		XSSFRow rowgrade = sheet.getRow(13);
		XSSFCell grade = rowgrade.getCell(1);
		grade.setCellValue(enseignant.getGrade().getTitre());
		// idnetifiant unique 
		XSSFRow rowidentifiant = sheet.getRow(14);
		XSSFCell identifiant = rowidentifiant.getCell(1);
		identifiant.setCellValue(enseignant.getIdentifiantUnique());
		// RIB 
		XSSFRow rowRIB = sheet.getRow(15);
		XSSFCell rib = rowRIB.getCell(1);
		rib.setCellValue(String.valueOf(enseignant.getRib()));
		// nombre d'heure semstre 1
		XSSFRow rowS1 = sheet.getRow(18);
		// corus semstre1
		XSSFCell coursS1 = rowS1.getCell(0);
		double VcoursS1 = enseignant.getChargeHoraireS1().getCours();
		coursS1.setCellValue(VcoursS1);
		// td semstre 1 
		XSSFCell tdS1 = rowS1.getCell(2);
		double VtdS1 = enseignant.getChargeHoraireS1().getTd();
		tdS1.setCellValue(VtdS1);
		// tp semstre 1
		XSSFCell tpS1 = rowS1.getCell(4);
		double VtpS1 = enseignant.getChargeHoraireS1().getTp() ;
		tpS1.setCellValue(VtpS1);
		// total semstre 1 
		XSSFCell totalS1 = rowS1.getCell(6);
		double VtotalS1 = Precision.round((  VtdS1 + VtpS1 + VcoursS1),3);
		totalS1.setCellValue(VtotalS1);
		// Row nobre d'heure semstre 2 
		XSSFRow rowS2 = sheet.getRow(22);
			// cours semstre 2
		XSSFCell coursS2 = rowS2.getCell(0);
		double VcoursS2 = enseignant.getChargeHoraireS2().getCours();
		coursS2.setCellValue(VcoursS2);
			// td semstre 2 
		XSSFCell tdS2 = rowS2.getCell(2);
		double VtdS2 = enseignant.getChargeHoraireS2().getTd();
		tdS2.setCellValue(VtdS2);
			// tp semstre 2 
		XSSFCell tpS2 = rowS2.getCell(4);
		double VtpS2 = enseignant.getChargeHoraireS2().getTp() ;
		tpS2.setCellValue(VtpS2);
		// total semstre 2 
		XSSFCell totalS2 = rowS2.getCell(6);
		double VtotalS2 = Precision.round((  VtdS2 + VtpS2 + VcoursS2),3);
		totalS2.setCellValue(VtotalS2);
		// Row nombre PFE 
		XSSFRow rowPFE = sheet.getRow(24);
		XSSFCell nbPFE = rowPFE.getCell(0);
		nbPFE.setCellValue(enseignant.getChargeHorairePFE().getNombre());
		
		// Row nombre total heure effectuée  
		XSSFRow RowHeureAndPFE = sheet.getRow(27);
		//	nombre cours avec pfe
		XSSFCell coursE = RowHeureAndPFE.getCell(0);
		double VcoursE = Precision.round(( VcoursS1 +VcoursS2 )/2,3);
		coursE.setCellValue(VcoursE);
		//	nombre td avec pfe 
		XSSFCell tdE = RowHeureAndPFE.getCell(2);
		double VtdE = Precision.round(( (VtdS1 +VtdS2 )/2)+enseignant.getChargeHorairePFE().getNombre(),3);
		tdE.setCellValue(VtdE);
		// nombre tp avec pfe 
		XSSFCell tpE = RowHeureAndPFE.getCell(4);
		double VtpE = Precision.round(( VtpS1 +VtpS2 )/2,3);
		tpE.setCellValue(VtpE);
		// total avec pfe 
		XSSFCell totalE = RowHeureAndPFE.getCell(6);
		double VtotalE = Precision.round(( VtpE + VtdE +VcoursE ),3);
		totalE.setCellValue(VtotalE);
		// Row pondiration 
		XSSFRow pondiration = sheet.getRow(31);
		// pondiration cours 
		XSSFCell coursP = pondiration.getCell(0);
		coursP.setCellValue(enseignant.getHeuresSupp().getCours());
		// pondiration td 
		XSSFCell tdP = pondiration.getCell(2);
		tdP.setCellValue(enseignant.getHeuresSupp().getTd());
		// pondriation tp 
		XSSFCell tpP = pondiration.getCell(4);
		tpP.setCellValue(enseignant.getHeuresSupp().getTp());
		// Directeur 
		XSSFRow RowDirecteur = sheet.getRow(36);
		XSSFCell directeur = RowDirecteur.getCell(3);
		directeur.setCellValue(findByFonctionTitreLike("Directeur").getPrenomNomArabe());
		
		
		//Precision.round(( ),3);
		
		fis.close();
		FileOutputStream fos=new FileOutputStream("src/main/resources/DossierEnseignant2.xlsx");
		workbook.write(fos);
		fos.close();
	}

	@Override
	public Enseignant findByFonctionTitreLike(String titre) {
	
		return enseignantRespository.findByFonctionTitreLike(titre);
	}

	@Override
	public void sendMail(Mail mail) {
		SimpleMailMessage sendMail= new SimpleMailMessage();
		//sendMail.setSubject("Demande d'inscription compte iset Béja");
		sendMail.setSubject(mail.getSubject());
		sendMail.setText(mail.getContent());
		sendMail.setTo(mail.getEmail());
		sendMail.setFrom("charguioussema66@gmail.com");
		
			emailSender.send(sendMail);
			
		
		/*MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			String htmlMsg = "<h3> "+mail.getContent() +"</h3>"
	                +"<img width='30px' height='30px'  src='src/main/resources/isetbeja.jpg'>";
			message.setContent(htmlMsg, "text/html");
			helper.setTo(mail.getEmail());
			helper.setSubject("Inscription à Iset Béja");
			emailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	@Override
	public Enseignant addPassword(Enseignant enseignant) {
		System.err.println(enseignant.getPassword());
		System.err.println(enseignant);
		String cryptedPassword = bCryptPasswordEncoder.encode(enseignant.getPassword());
		enseignant.setPassword(cryptedPassword);
		
		return enseignantRespository.save(enseignant);

	}

	
	
	
	
	

	
	
	
}
