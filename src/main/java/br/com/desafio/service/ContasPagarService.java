package br.com.desafio.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.desafio.builder.ContasPagarBuilder;
import br.com.desafio.domain.ContasPagar;
import br.com.desafio.exception.NotFoundException;
import br.com.desafio.repository.ContasPagarRepository;
import br.com.desafio.rest.request.ContasPagarInsertRequest;
import br.com.desafio.rest.request.ContasPagarUpdateRequest;

@Service
public class ContasPagarService {

	private ContasPagarRepository contasPagarRepository;

	public ContasPagar save(ContasPagarInsertRequest request) {
		ContasPagar contasPagar = ContasPagarBuilder.toEntity(request);
		return contasPagarRepository.save(contasPagar);
	}

	public ContasPagar update(ContasPagarUpdateRequest request) {
		ContasPagar contasPagar = contasPagarRepository.findById(request.getId())
				.orElseThrow(() -> new NotFoundException(
						String.format("Entidade Contas Pagar de id %s não existe na base de dados!", request.getId())));
		contasPagar = ContasPagarBuilder.copyFromFirstToSecond(request, contasPagar);
		return contasPagarRepository.save(contasPagar);
	}

	public ContasPagar getById(UUID id) {
		return contasPagarRepository.findById(id).orElseThrow(() -> new NotFoundException(
				String.format("Entidade Contas Pagar de id %s não existe na base de dados!", id)));
	}

	public Page<ContasPagar> getByFilters(LocalDate dataVencimento, String descricao, Pageable pageable) {
		Page<ContasPagar> result = contasPagarRepository.getByFilters(dataVencimento, descricao, pageable);

		if (result.isEmpty()) {
			throw new NotFoundException("Nenhum resultado para os filtros informados");
		}

		return result;
	}

}
