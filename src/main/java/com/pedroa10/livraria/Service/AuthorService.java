package com.pedroa10.livraria.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroa10.livraria.Repository.AuthorRepository;
import com.pedroa10.livraria.model.Author;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository aRepo;
	
	public Author createAuthor (Author author) {
		if(author.getName() == null || author.getName().trim().isEmpty()) {
			throw new IllegalArgumentException("O nome do Autor é obrigatório.");
		}
		if(aRepo.existsByName(author.getName())) {
			throw new IllegalArgumentException("Já existe um autor com esse nome.");
		}
		
		return aRepo.save(author);
	}
	
	public List<Author> getAllAuthors(){
		return aRepo.findAll();
	}
	
	public Author getAuthorById(Long id) {
		return aRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com o ID: " + id));
	}
	
	public Author updateAuthor(Long id, Author updateAuthor) {
		Author author = aRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado."));
		
		if(!author.getName().equals(updateAuthor.getName()) && aRepo.existsByName(updateAuthor.getName())) {
			throw new IllegalArgumentException("Já existe um autor com esse nome.");
		}
		
		author.setName(updateAuthor.getName());
		author.setBio(updateAuthor.getBio());
		author.setBirthDate(updateAuthor.getBirthDate());
		author.setNationality(updateAuthor.getNationality());
		
		return aRepo.save(author);
	}
	
	public void deleteAuthor(Long id) {
		Author author = aRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado."));
		
		if(!author.getBooks().isEmpty()) {
			throw new IllegalStateException("Não é possível excluir um author com livros associados.");
		}
		aRepo.deleteById(id);
	}
}