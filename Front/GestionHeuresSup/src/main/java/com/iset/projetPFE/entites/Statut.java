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
public class Statut implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	@OneToMany(mappedBy = "statut")
	@JsonBackReference
	private List<StagePFE> stagepfe;
	
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

	public List<StagePFE> getStagepfe() {
		return stagepfe;
	}
	public void setStagepfe(List<StagePFE> stagepfe) {
		this.stagepfe = stagepfe;
	}
	@Override
	public String toString() {
		return "Statut [id=" + id + ", titre=" + titre + "]";
	}
	
	
	

}
