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

import com.pedroa10.livraria.Exception.EntityNotFoundException;
import com.pedroa10.livraria.Service.AuthorService;
import com.pedroa10.livraria.model.Author;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@PostMapping
	public Author createAuthor(@RequestBody Author author) {
		return authorService.createAuthor(author);
	}
	
	@GetMapping
	public ResponseEntity<List<Author>> getAllAuthors(){
		List<Author> authors = authorService.getAllAuthors();
		return ResponseEntity.status(HttpStatus.CREATED).body(authors);
	}
	
	@GetMapping("/{id}")
	public Author getAuthorById(@PathVariable Long id) {
		return authorService.getAuthorById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author updateAuthor) {
		try {
			Author author = authorService.updateAuthor(id, updateAuthor);
			return ResponseEntity.ok(author);
			
		}catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
		try {
			authorService.deleteAuthor(id);
			return ResponseEntity.noContent().build();
			
		}catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
