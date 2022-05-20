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
public class Fonction implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	private String titreArabe;
	@OneToMany(mappedBy = "fonction")
	@JsonBackReference
	private List<Enseignant> enseignants;

	
	public Fonction(int id, String titre, String titreArabe) {
		
		this.id = id;
		this.titre = titre;
		this.titreArabe = titreArabe;
	}
	public Fonction(String titre, String titreArabe) {


		this.titre = titre;
		this.titreArabe = titreArabe;
	}
	public Fonction() {
		super();
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
		return "Fonction [id=" + id + ", titre=" + titre + ", titreArabe=" + titreArabe + "]";
	}
	
}
