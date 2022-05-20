package com.iset.projetPFE.entites;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Entity
public class HeurePFE extends ChargeHoraire implements Serializable {

	private int nombre;
	private double coefficient;
	
	
	
	
	public HeurePFE() {
	}
	
	public HeurePFE(int id,String anneeUniversitaire,int nombre, double coefficient) {
		super(id,anneeUniversitaire);
		this.nombre = nombre;
		this.coefficient = coefficient;
	}

	public int getNombre() {
		return nombre;
	}
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}
	public double getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	@Override
	public String toString() {
		return "HeurePFE [nombre=" + nombre + ", coefficient=" + coefficient + "]";
	}
	
	
	

}
