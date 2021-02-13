package com.desafio.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.model.entity.Usuario;
import com.desafio.model.repository.UsuarioRepository;
import com.desafio.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private final String USUARIO = "admin";
	private final String SENHA = "123456";
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		Usuario usuario = Usuario.builder().nome(USUARIO).senha(SENHA).id(1l).build();
		Mockito.when(repository.findByNomeContainingIgnoreCase(USUARIO)).thenReturn(Optional.of(usuario));
		
		Usuario result = service.autenticar(USUARIO, SENHA);
		
		Assertions.assertThat(result).isNotNull();
		
	}

}
