package br.com.desafio.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.desafio.constants.SituacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_contas_pagar")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContasPagar implements Serializable {
	
	private static final long serialVersionUID = 7724831383187268034L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private UUID id;
	
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	@Column(name = "valor")
	private BigDecimal valor;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoEnum situacao;

}
