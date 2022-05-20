package com.iset.projetPFE.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iset.projetPFE.SpringApplicationContext;
import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.entites.EnseignantLoginRequest;
import com.iset.projetPFE.services.EnseignantServices;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			EnseignantLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),
					EnseignantLoginRequest.class);
		 	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>());
			return authenticationManager.authenticate(authentication);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		User user = (User) authResult.getPrincipal();
		EnseignantServices enseignantServices = (EnseignantServices)SpringApplicationContext.getBean("enseignantService");
		Enseignant enseignant = enseignantServices.findByEmail(user.getUsername());
		String token = Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 9999999))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_JWT)
				.compact();
		
		
		response.addHeader(SecurityConstants.AUTORIZATION, SecurityConstants.BEARER + token);
		//response.getWriter().write("{\"token\": \""+ token + "\",\"id\": \""+ enseignant.getId()+"\",\"fonction\": \""+ enseignant.getFonction().getTitre()+"\"}");
		response.getWriter().write("{\"token\": \""+ token + "\",\"id\": \""+ enseignant.getId()+"\",\"fonction\": \""+ user.getAuthorities().iterator().next()+"\"}");
	}
	
	
}
