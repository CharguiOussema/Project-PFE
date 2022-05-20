package com.iset.projetPFE.entites;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class ChargeHoraireEffectif extends ChargeHoraire implements Serializable {
	

	private double td;
	private double cours;
	private double tp;
	
	public double getTd() {
		return td;
	}
	public void setTd(double td) {
		this.td = td;
	}
	public double getCours() {
		return cours;
	}
	public void setCours(double cours) {
		this.cours = cours;
	}
	public double getTp() {
		return tp;
	}
	public void setTp(double tp) {
		this.tp = tp;
	}
	@Override
	public String toString() {
		return "ChargeHoraireEffectif [td=" + td + ", cours=" + cours + ", tp=" + tp + "]";
	}

	
}
