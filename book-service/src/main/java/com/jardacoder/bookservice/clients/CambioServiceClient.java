package com.jardacoder.bookservice.clients;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jardacoder.bookservice.api.dto.CambioDto;

@FeignClient(name = "cambio-service")
public interface CambioServiceClient {

	@GetMapping("/cambio-service/{amount}/{from}/{to}")
	public CambioDto getCambio(@PathVariable BigDecimal amount, @PathVariable String from, @PathVariable String to);
}
