package com.desafio.api.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.desafio.api.dto.UsuarioDTO;
import com.desafio.exception.ErroAutenticacao;
import com.desafio.model.entity.Usuario;
import com.desafio.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest( controllers = UsuarioResource.class )
@AutoConfigureMockMvc
public class UsuarioResourceTest {
	
	static final String API = "/api/usuarios";
	static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	static final String USUARIO = "admin";
	static final String SENHA = "123456";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	UsuarioService service;
	
	@Test
	public void autenticarUsuario() throws Exception {
		UsuarioDTO dto = UsuarioDTO.builder().nome(USUARIO).senha(SENHA).build();
		Usuario usuario = Usuario.builder().id(1l).nome(USUARIO).senha(SENHA).build();
		Mockito.when(service.autenticar(USUARIO, SENHA)).thenReturn(usuario);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post( API.concat("/autenticar"))
													.accept( JSON )
													.contentType( JSON )
													.content(json);
		mvc.perform(request)
			.andExpect( MockMvcResultMatchers.status().isOk())
			.andExpect( MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
			.andExpect( MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()));
		
	}
	
	@Test
	public void erroAutenticacao() throws Exception {
		UsuarioDTO dto = UsuarioDTO.builder().nome(USUARIO).senha(SENHA).build();
		Mockito.when( service.autenticar(USUARIO, SENHA)).thenThrow(ErroAutenticacao.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API.concat("/autenticar"))
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

}
