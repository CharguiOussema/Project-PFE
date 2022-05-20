package com.iset.projetPFE.entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Etablissement implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	private int nombreProjetPFE;
	private int nombreEnseignant;
	private String anneeUniversitaire;
	private String periode;
	private String periodeArabe;
	private double nombreSomaine;
	
	
	public String getPeriodeArabe() {
		return periodeArabe;
	}
	public void setPeriodeArabe(String periodeArabe) {
		this.periodeArabe = periodeArabe;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public double getNombreSomaine() {
		return nombreSomaine;
	}
	public void setNombreSomaine(double nombreSomaine) {
		this.nombreSomaine = nombreSomaine;
	}
	public String getAnneeUniversitaire() {
		return anneeUniversitaire;
	}
	public void setAnneeUniversitaire(String anneeUniversitaire) {
		this.anneeUniversitaire = anneeUniversitaire;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getNombreProjetPFE() {
		return nombreProjetPFE;
	}
	public void setNombreProjetPFE(int nombreProjetPFE) {
		this.nombreProjetPFE = nombreProjetPFE;
	}

	public int getNombreEnseignant() {
		return nombreEnseignant;
	}
	public void setNombreEnseignant(int nombreEnseignant) {
		this.nombreEnseignant = nombreEnseignant;
	}

	@Override
	public String toString() {
		return "Etablissement [id=" + id + ", nom=" + nom + ", nombreProjetPFE=" + nombreProjetPFE
				+ ", nombreEnseignant=" + nombreEnseignant + "]";
	}
	

}
