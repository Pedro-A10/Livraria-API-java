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
import com.pedroa10.livraria.Service.BookService;
import com.pedroa10.livraria.model.Author;
import com.pedroa10.livraria.model.Book;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book createdBook = bookService.createBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
	}
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Book book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book updateBook) {
		try {
			Book book = bookService.updateBook(id, updateBook);
			return ResponseEntity.ok(book);
			
		}catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		try {
			bookService.deleteBook(id);
			return ResponseEntity.noContent().build();
			
		}catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}

