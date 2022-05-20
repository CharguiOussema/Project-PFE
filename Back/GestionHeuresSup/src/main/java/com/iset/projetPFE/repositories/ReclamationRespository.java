package com.iset.projetPFE.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Reclamation;

@Repository
public interface ReclamationRespository extends JpaRepository<Reclamation, Integer>{

	
	public List<Reclamation> findByTypeReclamationTypeLike(String type);
	public List<Reclamation> findByEnseignantDepartementTitreLikeAndTypeReclamationType(String titre,String type);
}
