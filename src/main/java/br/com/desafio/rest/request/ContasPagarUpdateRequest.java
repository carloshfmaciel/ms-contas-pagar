package br.com.desafio.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.desafio.constants.SituacaoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContasPagarUpdateRequest {
	
	@NotNull(message = "propriedade id deve ser informada!")
	private UUID id;

	@NotNull(message = "propriedade dataVencimento deve ser informada!")
	private LocalDate dataVencimento;
	
	private LocalDate dataPagamento;
	
	@NotNull(message = "propriedade valor deve ser informada!")
	private BigDecimal valor;
	
	@NotNull(message = "propriedade descricao deve ser informada!")
	private String descricao;
	
	@NotNull(message = "propriedade situacao deve ser informada!")
	private SituacaoEnum situacao;
	
}
