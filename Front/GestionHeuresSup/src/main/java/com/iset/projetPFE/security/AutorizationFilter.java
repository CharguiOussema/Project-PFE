package com.iset.projetPFE.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.iset.projetPFE.entites.Enseignant;
import com.iset.projetPFE.repositories.EnseignantRespository;
import io.jsonwebtoken.Jwts;



public class AutorizationFilter extends BasicAuthenticationFilter{
	
	private EnseignantRespository enseignantRespository;
	
	
	public AutorizationFilter(AuthenticationManager authenticationManager, EnseignantRespository enseignantRespository )
	{
		super(authenticationManager);
		this.enseignantRespository = enseignantRespository;
	}
	
	

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(SecurityConstants.AUTORIZATION);
		
		if(header==null || !header.startsWith(SecurityConstants.BEARER))
		{
			chain.doFilter(request, response);
			return;
		}
		
				Authentication authenticationToken= getAuth(request);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				chain.doFilter(request, response);
				
			
		}
	private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest request)
	{
		String token= request.getHeader(SecurityConstants.AUTORIZATION).replace(SecurityConstants.BEARER,"");
		if(token!=null)
		{
			String user = Jwts.parser()
					.setSigningKey(SecurityConstants.SECRET_JWT)
					.parseClaimsJws(token)
					.getBody().getSubject();
			if(user!=null)
			{
				Enseignant enseignant = enseignantRespository.findByEmail(user);
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority(enseignant.getFonction().getTitre()));
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
            return auth;
	}
		return null;
		}
		return null;
	}
	
	
	
	
	
}
