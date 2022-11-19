package com.jardacoder.cambioservice.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jardacoder.cambioservice.domain.model.Cambio;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long> {
	
	Optional<Cambio> findByFromAndTo(String from, String to);

}
