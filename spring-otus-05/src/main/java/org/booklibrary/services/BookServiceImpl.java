package org.booklibrary.services;

import lombok.RequiredArgsConstructor;
import org.booklibrary.dto.Book;
import org.springframework.stereotype.Service;
import org.booklibrary.dao.AuthorDao;
import org.booklibrary.dao.BookDao;
import org.booklibrary.dao.GenreDao;
import org.booklibrary.dto.Author;
import org.booklibrary.dto.Genr;
import org.booklibrary.exception.ObjectFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getByName(String name) {
        return bookDao.getByName(name);
    }

    @Override
    public List<Book> getByAuthor(String author) {
        return bookDao.getByAuthor(author);
    }

    @Override
    public List<Book> getByGenre(String genre) {
        return bookDao.getByGenre(genre);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorDao.getAll();
    }

    @Override
    public Book save(Book book) {
        if (book.getAuthor().getId() == null) {
            book.setAuthor(authorDao.insert(book.getAuthor()));
        }
        Set<Genr> genresWithId = new HashSet<>();
        book.getGenrs()
                .forEach( genre -> {
            if(genre.getId() == null){
                genresWithId.add(genreDao.insert(genre));
            }else {
                genresWithId.add(genre);
            }});
        book.setGenrs(genresWithId);
        return bookDao.insert(book);
    }

    @Override
    public Book updateBook(String name, String newName) {
       int status = bookDao.update(name, newName);
        if(status != 0 ){
            return bookDao.getByName(newName);
        }else{
            throw new ObjectFoundException(String.format("Can be update book with name %s book not found",name));
        }
    }

    @Override
    public boolean deleteById(int id) {
        return bookDao.deleteById(id);
    }
}
