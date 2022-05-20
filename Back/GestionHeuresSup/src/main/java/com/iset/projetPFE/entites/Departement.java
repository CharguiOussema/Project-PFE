package com.iset.projetPFE.entites;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Departement implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	private String titre;
	
	@OneToMany(mappedBy = "departement")
	@JsonBackReference
	private List<Enseignant> enseignants;
	
	
	
	public Departement() {
	
	}
	public Departement(String titre) {
		
		this.titre = titre;
	}
	public Departement(int id, String titre) {
		
		this.id = id;
		this.titre = titre;
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
	public List<Enseignant> getEnseignants() {
		return enseignants;
	}
	public void setEnseignants(List<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}
	

	@Override
	public String toString() {
		return "Departement [id=" + id + ", titre=" + titre + "]";
	}
	
	

}
