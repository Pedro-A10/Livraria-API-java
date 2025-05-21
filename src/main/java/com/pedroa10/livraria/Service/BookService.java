package com.pedroa10.livraria.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroa10.livraria.Exception.BusinessRuleException;
import com.pedroa10.livraria.Exception.EntityNotFoundException;
import com.pedroa10.livraria.Exception.ValidationException;
import com.pedroa10.livraria.Repository.AuthorRepository;
import com.pedroa10.livraria.Repository.BookRepository;
import com.pedroa10.livraria.model.Author;
import com.pedroa10.livraria.model.Book;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bRepo;
	@Autowired
	private AuthorRepository aRepo;
	
	public Book createBook(Book book) {
		if(book.getTitle() == null || book.getTitle().trim().isEmpty()) {
			throw new ValidationException("O título deve ser preenchido.");
		}
		if(book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
			throw new ValidationException("O ISBN deve ser preenchido.");
		}
		if(book.getAuthor() == null || book.getAuthor().getId() == null) {
			throw new ValidationException("Autor não informado.");
		}
		if(!book.getIsbn().matches("^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$")) {
			throw new ValidationException("Formato de ISBN inválido.");
		}
		if(bRepo.existsByIsbn(book.getIsbn())) {
			throw new BusinessRuleException("ISBN já cadastrado.");
		}
		
		Author author = aRepo.findById(book.getAuthor().getId()).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado."));
		book.setAuthor(author);

		return bRepo.save(book);
	}
	
	public List<Book> getAllBooks() {
		return bRepo.findAll();
	}
	
	public Book getBookById(Long id) {
		return bRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID" + id));
	}
	
	public Book updateBook(Long id, Book updateBook) {
		Book book = bRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado."));
		
		book.setIsbn(updateBook.getIsbn());
		book.setGenre(updateBook.getGenre());
		book.setAuthor(updateBook.getAuthor());
		book.setPubDate(updateBook.getPubDate());
		
		return bRepo.save(book);
	}
	
	public void deleteBook(Long id) {
		Book book = bRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado."));
		
		bRepo.deleteById(id);
	}
}
