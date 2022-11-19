package com.jardacoder.bookservice.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Foo")
@Slf4j
@RestController
@RequestMapping(path = "/book-service")
public class FooController {
	
	@Operation(summary = "Find a especific book by your id")
	@GetMapping("/foo")
	// @Retry(name = "foo", fallbackMethod = "fallbackFoo" )
	// @CircuitBreaker(name = "foo", fallbackMethod = "fallbackFoo" )
	//	@RateLimiter(name = "default", fallbackMethod = "fallbackLimit" )
	@Bulkhead(name = "default", fallbackMethod = "fallbackLimit" )
	public String foo() {
		log.info("Request received");
		// new RestTemplate().getForEntity("http://localhost:8080/naovai", String.class);
				
		return "foo";
	}
	
	
	public String fallbackFoo(Exception e) {
		return "Fallback for foo";
	}
	
	public String fallbackLimit(Exception e) {
		return "Limite estourado faixa";
	}

}
