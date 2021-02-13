package com.desafio.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void buscarUsuario() {
		Usuario usuario = criarEPersistirUsuario();
		
		Optional<Usuario> usuarioExistente = repository.findById(usuario.getId());
		
		assertThat(usuarioExistente.get()).isNotNull();
	}

	public static Usuario criarUsuario() {
		return Usuario.builder()
				.nome("admin")
				.senha("123456")
				.build();
	}
	
	private Usuario criarEPersistirUsuario() {
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		return usuario;
	}
	
}
