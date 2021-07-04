package org.booklibrary.services;

import org.booklibrary.dto.Author;
import org.booklibrary.dto.Book;

import java.util.List;

public interface BookService {

    Book getById(int id);

    Book getByName(String name);

    List<Book> getByAuthor(String author);

    List<Book> getByGenre(String genre);

    List<Book> getAll();

    List<Author> getAllAuthor();

    Book save(Book book);

    Book updateBook(String name, String newName);

    boolean deleteById(int id);
}
