package org.booklibrary.dao;


import org.booklibrary.dto.Book;

import java.util.List;

public interface BookDao {

    Integer count();

    Book getById(int id);

    Book getByName(String name);

    List<Book> getByAuthor(String author);

    List<Book> getByGenre(String genre);

    List<Book> getAll();

    Book insert(Book book);

    int update(String bookName, String newName);

    boolean deleteById(int id);
}