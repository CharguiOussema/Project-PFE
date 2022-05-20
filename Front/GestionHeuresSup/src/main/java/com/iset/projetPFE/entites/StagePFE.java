package com.iset.projetPFE.entites;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class StagePFE implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String sujet;
	private String societe;
	@ManyToOne(fetch = FetchType.EAGER)
	private Departement departement;
	@ManyToOne(fetch = FetchType.EAGER)
	private Enseignant enseignant;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	private EtudiantPFE etudiantPFE1;
	@OneToOne(fetch = FetchType.EAGER ,cascade = CascadeType.MERGE)
	
	private EtudiantPFE etudiantPFE2;
	@ManyToOne(fetch =FetchType.EAGER)
	private Statut statut;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public Departement getDepartement() {
		return departement;
	}
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	public Enseignant getEnseignant() {
		return enseignant;
	}
	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}
	public EtudiantPFE getEtudiantPFE1() {
		return etudiantPFE1;
	}
	public void setEtudiantPFE1(EtudiantPFE etudiantPFE1) {
		this.etudiantPFE1 = etudiantPFE1;
	}
	public EtudiantPFE getEtudiantPFE2() {
		return etudiantPFE2;
	}
	public void setEtudiantPFE2(EtudiantPFE etudiantPFE2) {
		this.etudiantPFE2 = etudiantPFE2;
	}
	public Statut getStatut() {
		return statut;
	}
	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	@Override
	public String toString() {
		return "StagePFE [id=" + id + ", sujet=" + sujet + ", societe=" + societe + "]";
	}
	
	
}
