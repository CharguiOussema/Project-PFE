package com.iset.projetPFE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.projetPFE.entites.HeurePFE;

@Repository
public interface HeurePFERespository extends JpaRepository<HeurePFE, Integer> {
	public HeurePFE findById(int id);

}
