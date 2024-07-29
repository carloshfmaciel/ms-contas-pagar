package br.com.desafio.rest.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.desafio.constants.SituacaoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContasPagarResponse {
	
	private UUID id;
	
	private LocalDate dataVencimento;
	
	private LocalDate dataPagamento;
	
	private BigDecimal valor;
	
	private String descricao;
	
	private SituacaoEnum situacao;

}
