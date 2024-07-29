package br.com.desafio.builder;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import br.com.desafio.rest.response.Response;

public class ResponseBuilder {

	private ResponseBuilder() {
	}

	public static Response toResponse(Page page) {
		Map<String, Object> meta = new HashMap<String, Object>();
		meta.put("pageNumber", page.getNumber() + 1);
		meta.put("pageSize", page.getSize());
		meta.put("totalItems", page.getTotalElements());
		meta.put("totalPages", page.getTotalPages());
		return new Response(page.getContent(), meta);
	}
}
