package com.desafio.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api.dto.UsuarioDTO;
import com.desafio.exception.ErroAutenticacao;
import com.desafio.model.entity.Usuario;
import com.desafio.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	private final UsuarioService service;
	
	@PostMapping("/autenticar")
	public ResponseEntity<Usuario> autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getNome(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		}catch (ErroAutenticacao e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
