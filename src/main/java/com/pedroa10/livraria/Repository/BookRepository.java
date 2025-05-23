package com.pedroa10.livraria.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroa10.livraria.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByNameContainingIgnoreCase(String name);
	
	Book findByisbn(String isbn);
	
	List<Book> findByAuthorId(Long AuthorId);
	
	boolean existsByIsbn(String isbn);
	
}
