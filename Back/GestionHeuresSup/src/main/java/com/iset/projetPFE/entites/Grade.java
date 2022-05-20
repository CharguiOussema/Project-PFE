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
public class Grade implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	private String titreArabe;
	private double due;
	private double maxDue;
	private double salaireCours;
	private double salaireTd;
	private double salaireTp;
	
	@OneToMany(mappedBy = "grade")
	@JsonBackReference
	private List<Enseignant> enseignants;
	
	
	public Grade() {
	
	}
	public Grade(String titre, String titreArabe) {
		this.titre = titre;
		this.titreArabe = titreArabe;
	}
	public Grade(int id, String titre, String titreArabe) {
		this.id = id;
		this.titre = titre;
		this.titreArabe = titreArabe;
	}
	
	
	public double getSalaireCours() {
		return salaireCours;
	}
	public void setSalaireCours(double salaireCours) {
		this.salaireCours = salaireCours;
	}
	public double getSalaireTd() {
		return salaireTd;
	}
	public void setSalaireTd(double salaireTd) {
		this.salaireTd = salaireTd;
	}
	public double getSalaireTp() {
		return salaireTp;
	}
	public void setSalaireTp(double salaireTp) {
		this.salaireTp = salaireTp;
	}
	public double getDue() {
		return due;
	}
	public void setDue(double due) {
		this.due = due;
	}
	public double getMaxDue() {
		return maxDue;
	}
	public void setMaxDue(double maxDue) {
		this.maxDue = maxDue;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getTitreArabe() {
		return titreArabe;
	}
	public void setTitreArabe(String titreArabe) {
		this.titreArabe = titreArabe;
	}
	
	public List<Enseignant> getEnseignants() {
		return enseignants;
	}
	public void setEnseignants(List<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}
	@Override
	public String toString() {
		return "Grade [id=" + id + ", titre=" + titre + ", titreArabe=" + titreArabe + "]";
	}
	
	

}
