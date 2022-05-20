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
public class Parcours implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	@OneToMany(mappedBy = "parcours")
	@JsonBackReference
	private List<EtudiantPFE> etudiantsPFE;
	

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
	public List<EtudiantPFE> getEtudiantsPFE() {
		return etudiantsPFE;
	}
	public void setEtudiantsPFE(List<EtudiantPFE> etudiantsPFE) {
		this.etudiantsPFE = etudiantsPFE;
	}
	
	@Override
	public String toString() {
		return "Parcours [id=" + id + ", titre=" + titre + "]";
	}
	
	

}
