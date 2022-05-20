package com.iset.projetPFE.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.Reclamation;
import com.iset.projetPFE.repositories.ReclamationRespository;

@Service
public class ReclamationService {

	@Autowired 
	private ReclamationRespository reclamationRespository;
	
	
	public List<Reclamation> findAll(){
		return reclamationRespository.findAll();
	}
	public Reclamation addReclamation(Reclamation reclamation) {
		return reclamationRespository.save(reclamation);
	}
	public void deleteById(int id) {
		 reclamationRespository.deleteById(id);
	}
	public List<Reclamation> findByTypeReclamationTypeLike(String type){
		return reclamationRespository.findByTypeReclamationTypeLike(type);
	}
	public List<Reclamation> findByDeuxType(String type1, String type2){
		List<Reclamation> list1 = reclamationRespository.findByTypeReclamationTypeLike(type1);
		List<Reclamation> list2 = reclamationRespository.findByTypeReclamationTypeLike(type2);
		if(list2.isEmpty()) {
			return list1;
		}else {
			 list1.addAll(list2);
			 System.err.println("size => "+list1.size() );
			 return list1;
		}
		
	}
	public List<Reclamation> findByDepartementAndDeuxType(String titre,String type1,String type2){
		List<Reclamation> list1 = reclamationRespository.findByEnseignantDepartementTitreLikeAndTypeReclamationType(titre,type1);
		List<Reclamation> list2 = reclamationRespository.findByEnseignantDepartementTitreLikeAndTypeReclamationType(titre,type2);
		if(list2.isEmpty()) {
			return list1;
		}else {
			 list1.addAll(list2);
			 System.err.println("size => "+list1.size() );
			 return list1;
		}
	}

	public Reclamation findById(int id) {
		Optional<Reclamation> optional = reclamationRespository.findById(id);
		if( optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
		
	}
	
}
