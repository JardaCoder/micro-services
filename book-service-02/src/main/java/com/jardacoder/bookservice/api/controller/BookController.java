package com.jardacoder.bookservice.api.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.jardacoder.bookservice.api.dto.CambioDto;
import com.jardacoder.bookservice.clients.CambioServiceClient;
import com.jardacoder.bookservice.domain.model.Book;
import com.jardacoder.bookservice.domain.repository.BookRepository;

@RestController
@RequestMapping(path = "/book-service")
public class BookController {
	
	
	@Autowired
	private Environment environment; 
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CambioServiceClient cambioServiceClient;
	
	
	@GetMapping("/{id}/{currency}")
	public Book findBook(@PathVariable Long id, @PathVariable String currency) {
		
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
		
		CambioDto cambio = cambioServiceClient.getCambio(book.getPrice(), "USD", currency);
		
		var applicationPort = environment.getProperty("local.server.port");
		book.setEnvironment(applicationPort);
		book.setPrice(cambio.getConvertedValue());
		return book;
	}

}
