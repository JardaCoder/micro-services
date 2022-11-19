package com.jardacoder.bookservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jardacoder.bookservice.domain.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
