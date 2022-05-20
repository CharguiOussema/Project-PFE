package com.iset.projetPFE.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Enseignant;

@Repository
public interface EnseignantRespository extends JpaRepository<Enseignant, Integer> {
	
	public Enseignant findByEmail(String email);
	public Enseignant findByCin(long cin);
	public List<Enseignant> findByActive(String active);
	public Enseignant findByChargeHorairePFEId(int id);
	public List<Enseignant> findByDepartementTitre(String titre);
	@Query("SELECT e FROM Enseignant e WHERE e.codeCouleur = 'green' OR e.codeCouleur = 'orange' ")
	public List<Enseignant> findByCodeCouleurWithJPQL();
	public Enseignant findByFonctionTitreLike(String titre);

	

}
