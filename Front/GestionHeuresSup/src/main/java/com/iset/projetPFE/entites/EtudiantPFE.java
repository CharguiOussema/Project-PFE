package com.iset.projetPFE.entites;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class EtudiantPFE implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	private String prenomNom;
	private long cin;
	@ManyToOne(fetch =FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	private Parcours parcours;

	
	
	public long getCin() {
		return cin;
	}
	public void setCin(long cin) {
		this.cin = cin;
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
	public void setPrenomNom(String prenomNom) {
		this.prenomNom = prenomNom;
	}
	public Parcours getParcours() {
		return parcours;
	}
	public void setParcours(Parcours parcours) {
		this.parcours = parcours;
	}
	@Override
	public String toString() {
		return "EtudiantPFE [id=" + id + ", prenomNom=" + prenomNom + ", cin=" + cin + ", parcours=" + parcours + "]";
	}
	

	

	

}
