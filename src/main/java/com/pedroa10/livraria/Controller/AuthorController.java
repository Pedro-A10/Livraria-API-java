 package com.pedroa10.livraria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroa10.livraria.Service.AuthorService;
import com.pedroa10.livraria.model.Author;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@PostMapping
	public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
		Author createdAuthor = authorService.createAuthor(author);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
	}
	
	@GetMapping
	public ResponseEntity<List<Author>> getAllAuthors() {
		List<Author> authors = authorService.getAllAuthors();
		return ResponseEntity.ok(authors);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
		Author author = authorService.getAuthorById(id);
		return ResponseEntity.ok(author);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAuthor(@Valid @PathVariable Long id, @RequestBody Author updateAuthor) {
		Author author = authorService.updateAuthor(id, updateAuthor);
		return ResponseEntity.ok(author);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
		authorService.deleteAuthor(id);
		return ResponseEntity.noContent().build();
	}
}
