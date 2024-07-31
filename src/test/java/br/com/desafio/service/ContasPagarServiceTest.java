package br.com.desafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import br.com.desafio.builder.ContasPagarBuilder;
import br.com.desafio.constants.SituacaoEnum;
import br.com.desafio.domain.ContasPagar;
import br.com.desafio.exception.NotFoundException;
import br.com.desafio.repository.ContasPagarRepository;
import br.com.desafio.rest.request.ContasPagarInsertRequest;
import br.com.desafio.rest.request.ContasPagarUpdateRequest;

@DisplayName("Writing assertions to ContasPagarService")
class ContasPagarServiceTest {

	@InjectMocks
	private ContasPagarService contasPagarService;

	@Mock
	private ContasPagarRepository contasPagarRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
//		mockStatic(ContasPagarBuilder.class);
	}

	@Test
	void testConstructorMethod() {
		ContasPagarService instance = new ContasPagarService(contasPagarRepository);
		assertNotNull(instance);
	}

	@Test
	void testSaveMethod() {
		try (MockedStatic<ContasPagarBuilder> mocked = mockStatic(ContasPagarBuilder.class)) {
			ContasPagarInsertRequest request = ContasPagarInsertRequest.builder().dataVencimento(LocalDate.now())
					.dataPagamento(LocalDate.now()).valor(BigDecimal.valueOf(10.0d)).descricao("Alguma descrição")
					.situacao(SituacaoEnum.ABERTO).build();

			UUID id = UUID.randomUUID();

			ContasPagar contasPagarToSave = ContasPagar.builder().id(id).dataVencimento(request.getDataVencimento())
					.dataPagamento(request.getDataPagamento()).valor(request.getValor())
					.descricao(request.getDescricao()).situacao(request.getSituacao()).build();

			when(ContasPagarBuilder.toEntity(any())).thenReturn(contasPagarToSave);
			when(contasPagarService.save(any())).thenReturn(contasPagarToSave);

			mocked.when(() -> ContasPagarBuilder.toEntity(request)).thenReturn(contasPagarToSave);

			ContasPagar contasPagar = contasPagarService.save(request);

			assertNotNull(contasPagar);
			assertEquals(id, contasPagar.getId());
		}
	}

	@Test
	void testUpdateSuccess() {

		try (MockedStatic<ContasPagarBuilder> mocked = mockStatic(ContasPagarBuilder.class)) {
			ContasPagarUpdateRequest request = ContasPagarUpdateRequest.builder().id(UUID.randomUUID())
					.dataVencimento(LocalDate.now()).dataPagamento(LocalDate.now()).valor(BigDecimal.valueOf(10.0d))
					.descricao("Alguma descrição").situacao(SituacaoEnum.ABERTO).build();

			ContasPagar contasPagarToUpdate = ContasPagar.builder().id(request.getId())
					.dataVencimento(request.getDataVencimento()).dataPagamento(request.getDataPagamento())
					.valor(request.getValor()).descricao(request.getDescricao()).situacao(request.getSituacao())
					.build();

			when(contasPagarRepository.findById(any())).thenReturn(Optional.of(contasPagarToUpdate));
			mocked.when(() -> ContasPagarBuilder.copyFromFirstToSecond(request, contasPagarToUpdate))
					.thenReturn(contasPagarToUpdate);
			when(contasPagarRepository.save(any())).thenReturn(contasPagarToUpdate);

			ContasPagar contasPagar = contasPagarService.update(request);

			assertNotNull(contasPagar);
		}
	}

	@Test
	void testUpdateWhenIdNotExistsItMustThrowNotFoundException() {
		ContasPagarUpdateRequest request = ContasPagarUpdateRequest.builder().id(UUID.randomUUID())
				.dataVencimento(LocalDate.now()).dataPagamento(LocalDate.now()).valor(BigDecimal.valueOf(10.0d))
				.descricao("Alguma descrição").situacao(SituacaoEnum.ABERTO).build();

		when(contasPagarRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			contasPagarService.update(request);
		});
	}

	@Test
	void testGetByIdWhenItExistsItMustReturnIt() {
		UUID id = UUID.randomUUID();

		ContasPagar contasPagar = ContasPagar.builder().id(UUID.randomUUID()).dataVencimento(LocalDate.now())
				.dataPagamento(LocalDate.now()).valor(BigDecimal.valueOf(10.0d)).descricao("Alguma descrição")
				.situacao(SituacaoEnum.ABERTO).build();

		when(contasPagarRepository.findById(any())).thenReturn(Optional.of(contasPagar));

		ContasPagar result = contasPagarService.getById(id);

		assertNotNull(result);
	}

	@Test
	void testGetByIdWhenItDoesNotExistsItMustThrowNotFoundException() {
		UUID id = UUID.randomUUID();

		when(contasPagarRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			contasPagarService.getById(id);
		});
	}

	@Test
	void testGetByFiltersWhenThereIsResultItMustReturnIt() {
		ContasPagar contasPagar = ContasPagar.builder().id(UUID.randomUUID()).dataVencimento(LocalDate.now())
				.dataPagamento(LocalDate.now()).valor(BigDecimal.valueOf(10.0d)).descricao("Alguma descrição")
				.situacao(SituacaoEnum.ABERTO).build();

		PageRequest pageRequest = PageRequest.of(0, 50);

		when(contasPagarRepository.getByFilters(any(), any(), any()))
				.thenReturn(new PageImpl<>(List.of(contasPagar), pageRequest, 1));

		Page<ContasPagar> pageResult = contasPagarService.getByFilters(LocalDate.now(), "Some description",
				pageRequest);

		assertNotNull(pageResult);
		assertEquals(1, pageResult.getTotalElements());
	}

	@Test
	void testGetByFiltersWhenThereIsNoResultItMustThrowNotFoundException() {
		PageRequest pageRequest = PageRequest.of(0, 50);

		when(contasPagarRepository.getByFilters(any(), any(), any()))
				.thenReturn(new PageImpl<>(List.of(), pageRequest, 0));

		assertThrows(NotFoundException.class, () -> {
			contasPagarService.getByFilters(LocalDate.now(), "Some description", pageRequest);
		});
	}

	@Test
	void testImportByFiles() throws IOException {
		ContasPagar contasPagar = ContasPagar.builder().id(UUID.randomUUID()).dataVencimento(LocalDate.now())
				.dataPagamento(LocalDate.now()).valor(BigDecimal.valueOf(10.0d)).descricao("Alguma descrição")
				.situacao(SituacaoEnum.ABERTO).build();

		String csv = """
					Data_Vencimento,Data_Pagamento,Valor,Descricao,Situacao
					2024-08-15,,184.30,Conta de Água,ABERTO
					2024-08-10,,237.50,Conta de Luz,ABERTO
					2024-08-05,,139.90,Conta de Internet,ABERTO
					2024-08-07,,90.00,Conta de Gás,ABERTO
				""";

		MockMultipartFile multipartFile = new MockMultipartFile("data", "contas-pagar.csv", "text/plain",
				csv.getBytes());

		when(contasPagarRepository.saveAll(anyCollection())).thenReturn(List.of(contasPagar));

		contasPagarService.importByFile(multipartFile);
	}

}
