package com.iset.projetPFE.entites;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ChargeHoraire implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String anneeUniversitaire;
	
	
	public ChargeHoraire() {
	}
	public ChargeHoraire(int id, String anneeUniversitaire) {
		this.id = id;
		this.anneeUniversitaire = anneeUniversitaire;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnneeUniversitaire() {
		return anneeUniversitaire;
	}
	public void setAnneeUniversitaire(String anneeUniversitaire) {
		this.anneeUniversitaire = anneeUniversitaire;
	}
	@Override
	public String toString() {
		return "ChargeHoraire [id=" + id + ", anneeUniversitaire=" + anneeUniversitaire + "]";
	}


	

}
