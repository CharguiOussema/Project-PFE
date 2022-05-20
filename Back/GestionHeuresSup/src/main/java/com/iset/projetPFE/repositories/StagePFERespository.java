package com.iset.projetPFE.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.StagePFE;

@Repository
public interface StagePFERespository extends JpaRepository<StagePFE, Integer>{
	
	public List<StagePFE> findByEnseignantId(int id);
	public List<StagePFE> findByDepartementTitre(String titre);
	public List<StagePFE> findByDepartementId(int id);

}
