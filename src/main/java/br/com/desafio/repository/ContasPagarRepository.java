package br.com.desafio.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.desafio.domain.ContasPagar;

@Repository
public interface ContasPagarRepository
		extends JpaRepository<ContasPagar, UUID>, PagingAndSortingRepository<ContasPagar, UUID> {

	@Query("""
			SELECT cp FROM ContasPagar cp
			WHERE (:dataVencimento IS NULL OR cp.dataVencimento = :dataVencimento)
			AND (:descricao IS NULL OR trim(lower(cp.descricao)) LIKE trim(lower(concat('%', :descricao, '%'))))
			ORDER BY cp.dataVencimento
			""")
	public Page<ContasPagar> getByFilters(@Param("dataVencimento") LocalDate dataVencimento,
			@Param("descricao") String descricao, Pageable page);

}
