package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.Etablissement;

@Repository
public interface EtablissementRespository extends JpaRepository<Etablissement, Integer> {

}
