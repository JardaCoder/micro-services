package com.jardacoder.cambioservice.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jardacoder.cambioservice.domain.model.Cambio;
import com.jardacoder.cambioservice.domain.repository.CambioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "cambio-service")
@Tag(name = "Cambio Endpoints")
public class CambioController {
	
	
	@Autowired
	private Environment environment; 
	
	@Autowired
	private CambioRepository cambioRepository;
	
	@Operation(summary = "Find a cambio conversion")
	@GetMapping("/{amount}/{from}/{to}")
	public Cambio getCambio(@PathVariable BigDecimal amount,
			@PathVariable String from,
			@PathVariable String to ) throws UnknownHostException {
		
		Cambio cambio = cambioRepository.findByFromAndTo(from, to)
				.orElseThrow(() -> new RuntimeException("Currency Unsupported"));
	
		log.info("getCambio is called witg -> {}, {} and {}", amount, from, to);
		var applicationPort = environment.getProperty("local.server.port");
		var localHost = InetAddress.getLocalHost(); 
		
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount).setScale(2, RoundingMode.CEILING);
		
		cambio.setEnvironment(String.format("host: %s  name: %s", localHost.getHostAddress(), localHost.getHostName()));
		cambio.setConvertedValue(convertedValue);
		
		return cambio;
	}

}
