package com.app.aforo255segurdad.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.app.aforo255segurdad.models.entity.Usuario;

public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	public Usuario findByUsername( @Param("nombre") String username);
	
}
