package org.booklibrary.dao;

import org.booklibrary.dto.Genr;

import java.util.List;

public interface GenreDao {

    Integer count();

    Genr getByName(String name);

    List<Genr> getAll();

    Genr insert(Genr genr);
}
