package br.com.desafio.rest.controller.swagger;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafio.rest.request.ContasPagarInsertRequest;
import br.com.desafio.rest.request.ContasPagarUpdateRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;

@OpenAPIDefinition(info = @Info(title = "Serviço Gestão de Contas a Pagar - OpenAPI 3.0", version = "v1.0", description = ""))
public interface ContasPagarSwagger {
	
	@Operation(summary = "Insere um novo registro de contas a pagar", description = "Endpoint que insere um novo registro de contas a pagar!")
	@Tags(value = @Tag(name = "Contas a Pagar", description = "Contas a Pagar"))
	@PostMapping("")
	public ResponseEntity save(@RequestBody @Valid ContasPagarInsertRequest request);
	
	@Operation(summary = "Atualiza os dados de um registro de contas a pagar", description = "Endpoint que atualiza os dados de um registro de contas a pagar!")
	@Tags(value = @Tag(name = "Contas a Pagar", description = "Contas a Pagar"))
	@PutMapping("")
	public ResponseEntity update(@RequestBody ContasPagarUpdateRequest request);
	
	@Operation(summary = "Pesquisa registro de contas a pagar pelo id", description = "Endpoint que pesquisa registro de contas a pagar pelo id!")
	@Tags(value = @Tag(name = "Contas a Pagar", description = "Contas a Pagar"))
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") UUID id);
	
	@Operation(summary = "Pesquisa registros de contas a pagar por filtros", description = "Endpoint que pesquisa registros de contas a pagar por filtros!")	
	@Tags(value = @Tag(name = "Contas a Pagar", description = "Contas a Pagar"))
	@GetMapping("")
	public ResponseEntity getByFilters(
			@RequestParam(value = "dataVencimento", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize);

	@Operation(summary = "Import de registros de contas a pagar através de arquivos csv", description = "Endpoint que importa registros de contas a pagar através de arquivos csv!")	
	@Tags(value = @Tag(name = "Contas a Pagar", description = "Contas a Pagar"))
	@PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity importByFile(@RequestParam("file") MultipartFile file);
	
}
