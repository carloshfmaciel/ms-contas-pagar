package br.com.desafio.builder;

import java.util.List;

import br.com.desafio.domain.ContasPagar;
import br.com.desafio.rest.request.ContasPagarInsertRequest;
import br.com.desafio.rest.request.ContasPagarUpdateRequest;
import br.com.desafio.rest.response.ContasPagarResponse;

public class ContasPagarBuilder {

	private ContasPagarBuilder() {
		super();
	}

	public static ContasPagar toEntity(ContasPagarInsertRequest request) {
		return ContasPagar.builder()
				.dataVencimento(request.getDataVencimento())
				.dataPagamento(request.getDataPagamento())
				.valor(request.getValor())
				.descricao(request.getDescricao())
				.situacao(request.getSituacao())				
				.build();
	}

	public static ContasPagarResponse toResponse(ContasPagar entity) {
		return ContasPagarResponse.builder()
				.id(entity.getId())
				.dataVencimento(entity.getDataVencimento())
				.dataPagamento(entity.getDataPagamento())
				.valor(entity.getValor())
				.descricao(entity.getDescricao())
				.situacao(entity.getSituacao())
				.build();
	}
	
	public static List<ContasPagarResponse> toResponse(List<ContasPagar> entities) {
		return entities.stream().map(ContasPagarBuilder::toResponse).toList();
	}

	public static ContasPagar copyFromFirstToSecond(ContasPagarUpdateRequest request, ContasPagar entity) {
		entity.setDataVencimento(request.getDataVencimento());
		entity.setDataPagamento(request.getDataPagamento());
		entity.setDescricao(request.getDescricao());
		entity.setValor(request.getValor());
		entity.setSituacao(request.getSituacao());
		return entity;
	}

}
