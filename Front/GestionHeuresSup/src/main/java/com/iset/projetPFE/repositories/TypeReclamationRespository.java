package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.TypeReclamation;

@Repository
public interface TypeReclamationRespository extends JpaRepository<TypeReclamation, Integer> {

}
