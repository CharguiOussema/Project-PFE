package com.iset.projetPFE.entites;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Enseignant implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String prenomNom ;
	private String prenomNomArabe ;
	private long rib;
	private long cin;
	private long identifiantUnique ;
	private long numTel;
	private String email;
	private LocalDate dateNaissance;
	private String lieuNaissance;
	private String lieuNaissanceArabe;
	private double absence;
	private String active;
	private String password;
	private String codeCouleur;
	
	@ManyToOne(fetch =FetchType.EAGER,cascade = {CascadeType.MERGE})
	private Fonction fonction;
	@ManyToOne(fetch =FetchType.EAGER,cascade = {CascadeType.MERGE})
	private Grade grade;
	@ManyToOne(cascade = {CascadeType .MERGE})
	private Departement departement;
	@JsonBackReference
	@OneToMany(mappedBy = "enseignant")
	private List<StagePFE> stagesPFE;
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private HeurePFE chargeHorairePFE;
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private ChargeHoraireEffectif chargeHoraireS1;
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private ChargeHoraireEffectif chargeHoraireS2;
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private ChargeHoraireEffectif heuresSupp;
	
	
	

	public String getCodeCouleur() {
		return codeCouleur;
	}
	public void setCodeCouleur(String codeCouleur) {
		this.codeCouleur = codeCouleur;
	}
	public ChargeHoraireEffectif getHeuresSupp() {
		return heuresSupp;
	}
	public void setHeuresSupp(ChargeHoraireEffectif heuresSupp) {
		this.heuresSupp = heuresSupp;
	}
	public ChargeHoraireEffectif getChargeHoraireS1() {
		return chargeHoraireS1;
	}
	public void setChargeHoraireS1(ChargeHoraireEffectif chargeHoraireS1) {
		this.chargeHoraireS1 = chargeHoraireS1;
	}
	public ChargeHoraireEffectif getChargeHoraireS2() {
		return chargeHoraireS2;
	}
	public void setChargeHoraireS2(ChargeHoraireEffectif chargeHoraireS2) {
		this.chargeHoraireS2 = chargeHoraireS2;
	}
	public List<StagePFE> getStagesPFE() {
		return stagesPFE;
	}
	public void setStagesPFE(List<StagePFE> stagesPFE) {
		this.stagesPFE = stagesPFE;
	}
	public HeurePFE getChargeHorairePFE() {
		return chargeHorairePFE;
	}
	public void setChargeHorairePFE(HeurePFE chargeHorairePFE) {
		this.chargeHorairePFE = chargeHorairePFE;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrenomNom() {
		return prenomNom;
	}
	public void setPrenomNom(String prenom) {
		this.prenomNom = prenom;
	}
	public String getPrenomNomArabe() {
		return prenomNomArabe;
	}
	public void setPrenomNomArabe(String prenomArabe) {
		this.prenomNomArabe = prenomArabe;
	}
	
	
	public long getRib() {
		return rib;
	}



	public void setRib(long rib) {
		this.rib = rib;
	}



	public long getCin() {
		return cin;
	}
	public void setCin(long cin) {
		this.cin = cin;
	}
	public long getIdentifiantUnique() {
		return identifiantUnique;
	}
	public void setIdentifiantUnique(long identifiantUnique) {
		this.identifiantUnique = identifiantUnique;
	}
	public long getNumTel() {
		return numTel;
	}
	public void setNumTel(long numTel) {
		this.numTel = numTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getLieuNaissance() {
		return lieuNaissance;
	}
	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}
	public String getLieuNaissanceArabe() {
		return lieuNaissanceArabe;
	}
	public void setLieuNaissanceArabe(String lieuNaissanceArabe) {
		this.lieuNaissanceArabe = lieuNaissanceArabe;
	}
	public double getAbsence() {
		return absence;
	}
	public void setAbsence(double absence) {
		this.absence = absence;
	}
	
	public Fonction getFonction() {
		return fonction;
	}
	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public Grade getGrade() {
		return grade;
	}


	public void setGrade(Grade grade) {
		this.grade = grade;
	}


	public Departement getDepartement() {
		return departement;
	}


	public void setDepartement(Departement departement) {
		this.departement = departement;
	}


	@Override
	public String toString() {
		return "Enseignant [id=" + id + ", prenomNom=" + prenomNom + ", prenomNomArabe=" + prenomNomArabe + ", rib="
				+ rib + ", cin=" + cin + ", identifiantUnique=" + identifiantUnique + ", numTel=" + numTel + ", email="
				+ email + ", dateNaissance=" + dateNaissance + ", lieuNaissance=" + lieuNaissance
				+ ", lieuNaissanceArabe=" + lieuNaissanceArabe + ", absence=" + absence + ", active=" + active
				+ ", password=" + password + ", fonction=" + fonction + ", grade=" + grade + ", departement="
				+ departement +"]";
	}






	
	
	
	
}
