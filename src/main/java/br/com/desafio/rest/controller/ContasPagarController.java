package br.com.desafio.rest.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafio.builder.ContasPagarBuilder;
import br.com.desafio.builder.ResponseBuilder;
import br.com.desafio.rest.controller.swagger.ContasPagarSwagger;
import br.com.desafio.rest.request.ContasPagarInsertRequest;
import br.com.desafio.rest.request.ContasPagarUpdateRequest;
import br.com.desafio.service.ContasPagarService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/contas-pagar")
public class ContasPagarController implements ContasPagarSwagger {

	private ContasPagarService contasPagarService;

	public ContasPagarController(ContasPagarService contasPagarService) {
		super();
		this.contasPagarService = contasPagarService;
	}

	@PostMapping("")
	public ResponseEntity save(@RequestBody @Valid ContasPagarInsertRequest request) {
		return ResponseEntity.ok(ContasPagarBuilder.toResponse(contasPagarService.save(request)));
	}

	@PutMapping("")
	public ResponseEntity update(@RequestBody ContasPagarUpdateRequest request) {
		return ResponseEntity.ok(ContasPagarBuilder.toResponse(contasPagarService.update(request)));
	}

	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") UUID id) {
		return ResponseEntity.ok(ContasPagarBuilder.toResponse(contasPagarService.getById(id)));
	}

	@GetMapping("")
	public ResponseEntity getByFilters(
			@RequestParam(value = "dataVencimento", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		return ResponseEntity.ok(ResponseBuilder.toResponse(
				contasPagarService.getByFilters(dataVencimento, descricao, PageRequest.of(pageNumber, pageSize))));
	}

	@PostMapping("/import")
	public ResponseEntity importByFile(MultipartFile file) {
		return ResponseEntity.ok(null);
	}

}
