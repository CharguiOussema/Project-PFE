package com.iset.projetPFE.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.HeurePFE;
import com.iset.projetPFE.repositories.HeurePFERespository;

@Service
public class HeurePFEService {

	@Autowired
	private HeurePFERespository heurePFERespository;
	
	public HeurePFE findById(int id) {
		return heurePFERespository.findById(id);
	}
	

}
