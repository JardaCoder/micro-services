package com.jardacoder.bookservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jardacoder.bookservice.api.dto.CambioDto;
import com.jardacoder.bookservice.clients.CambioServiceClient;
import com.jardacoder.bookservice.domain.model.Book;
import com.jardacoder.bookservice.domain.repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/book-service")
@Tag(name = "Book Endpoints")
public class BookController {
	
	
	@Autowired
	private Environment environment; 
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CambioServiceClient cambioServiceClient;
	
	@Operation(summary = "Find a especific book by your id")
	@GetMapping("/{id}/{currency}")
	public Book findBook(@PathVariable Long id, @PathVariable String currency) {
		
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
		
		CambioDto cambio = cambioServiceClient.getCambio(book.getPrice(), "USD", currency);
		
		var applicationPort = environment.getProperty("local.server.port");
		book.setEnvironment(String.format("Book: %s  Cambio: %s", applicationPort, cambio.getEnvironment()));
		book.setPrice(cambio.getConvertedValue());
		log.error("nem deu erro");
		return book;
	}

}
