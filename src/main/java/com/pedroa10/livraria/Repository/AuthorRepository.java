package com.pedroa10.livraria.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroa10.livraria.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

	List<Author> findByAuthorContainingIgnoreCase(String name);
	
	boolean existsByName(String name);
}
