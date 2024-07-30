package br.com.desafio.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafio.builder.ContasPagarBuilder;
import br.com.desafio.constants.SituacaoEnum;
import br.com.desafio.domain.ContasPagar;
import br.com.desafio.exception.NotFoundException;
import br.com.desafio.repository.ContasPagarRepository;
import br.com.desafio.rest.request.ContasPagarInsertRequest;
import br.com.desafio.rest.request.ContasPagarUpdateRequest;

@Service
public class ContasPagarService {

	private ContasPagarRepository contasPagarRepository;

	public ContasPagarService(ContasPagarRepository contasPagarRepository) {
		super();
		this.contasPagarRepository = contasPagarRepository;
	}

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

	public List<ContasPagar> importByFile(MultipartFile file) {
		try (BufferedReader bReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(bReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			
			List<ContasPagar> contasPagarList = new ArrayList<ContasPagar>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				ContasPagar contasPagar = ContasPagar.builder()
						.dataVencimento(LocalDate.parse(csvRecord.get("Data_Vencimento")))
						.dataPagamento(csvRecord.get("Data_Pagamento").isEmpty() ? null : LocalDate.parse(csvRecord.get("Data_Pagamento")))
						.valor(new BigDecimal(csvRecord.get("Valor")))
						.descricao(csvRecord.get("Descricao"))
						.situacao(SituacaoEnum.valueOf(csvRecord.get("Situacao")))
						.build();
				contasPagarList.add(contasPagar);
			}

			return contasPagarRepository.saveAll(contasPagarList);
		} catch (IOException e) {
			throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
		}
	}
}
