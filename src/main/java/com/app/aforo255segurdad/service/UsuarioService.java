package com.app.aforo255segurdad.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.jni.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.aforo255segurdad.dao.UsuarioDao;
import com.app.aforo255segurdad.models.entity.Usuario;


@Service
public class UsuarioService implements UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioDao client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = client.findByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Error Login");
		}
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map( role -> new SimpleGrantedAuthority(role.getNombre() ) )
				.peek(authority -> log.info("Role:"+authority.getAuthority()))
				.collect(Collectors.toList());
		log.info("usuario ahtenticado: "+ username );
		return new org.springframework.security.core.userdetails.User( usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities );
	}

}
