package com.iset.projetPFE.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.iset.projetPFE.repositories.EnseignantRespository;
import com.iset.projetPFE.services.EnseignantService;
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private EnseignantService enseignantService;

	private EnseignantRespository enseignantRespository;

	public WebSecurity(EnseignantService enseignantService, EnseignantRespository enseignantRespository) {
		this.enseignantService = enseignantService;
		this.enseignantRespository = enseignantRespository;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http = http.cors().and().csrf().disable();
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/Enseignant/add").permitAll()
		.antMatchers(HttpMethod.POST,"Enseignant/readEnseignantExcel").permitAll()
		.antMatchers(HttpMethod.PUT,"/Enseignant/**").permitAll()
		.antMatchers(HttpMethod.PUT,"/StagePFE/**").permitAll()
		.antMatchers(HttpMethod.GET,"/Enseignant/**").permitAll()
		//.antMatchers(HttpMethod.GET,"/Enseignant/getById/{id}").permitAll()
		.antMatchers(HttpMethod.POST,"/StagePFE/**").permitAll()
		.antMatchers(HttpMethod.GET,"/StagePFE/**").hasAnyAuthority("Enseignant","Directeur","Directeur de departement","ressource humaine","directeur visiteur")
		.antMatchers(HttpMethod.GET,"/Departement/**").permitAll()
		.antMatchers(HttpMethod.GET,"/Fonction/**").permitAll()
		.antMatchers(HttpMethod.GET,"/Etudiant/**").permitAll()
		.antMatchers(HttpMethod.GET,"/Grade/**").permitAll()
		.antMatchers(HttpMethod.GET,"/Parcours/**").permitAll()
		.antMatchers(HttpMethod.GET,"/Statut/**").permitAll()

		.anyRequest().authenticated()
		.and().addFilter(new AuthenticationFilter(authenticationManager()))
		.addFilter(new AutorizationFilter(authenticationManager(),this.enseignantRespository))
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.httpBasic();
	
		//.antMatchers(HttpMethod.GET,"/Enseignant/**").permitAll()
	//	.antMatchers(HttpMethod.GET,"/Enseignant/**").hasAnyAuthority("Directeur de departement","Enseignant")
		//.antMatchers(HttpMethod.GET,"/StagePFE/getAll").permitAll()
		//.antMatchers(HttpMethod.GET,"Enseignant/getAll").hasAnyAuthority("Enseignant","Directeur","Directeur de departement")

	}
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(enseignantService).passwordEncoder(bCryptPasswordEncoder());
	}
}
