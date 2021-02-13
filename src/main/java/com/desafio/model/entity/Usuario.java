package com.desafio.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "usuario")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "senha", nullable = false)
	@JsonIgnore
	private String senha;

}
