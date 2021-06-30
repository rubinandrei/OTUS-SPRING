package org.booklibrary.dao;

import org.booklibrary.dto.Author;

import java.util.List;

public interface AuthorDao {

    Integer count();

    Author getByName(String name);

    Author insert(Author author);

    List<Author> getAll();


}
