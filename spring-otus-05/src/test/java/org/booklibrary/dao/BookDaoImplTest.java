package org.booklibrary.dao;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.booklibrary.dto.Author;
import org.booklibrary.dto.Book;
import org.booklibrary.dto.Genr;
import org.booklibrary.exception.ObjectFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDaoImpl.class)
@DisplayName(value = "DAO book")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class BookDaoImplTest {

    private static final int BOOKS_COUNT = 3;
    private static final String BOOKS_AUTHOR_NAME = "Alexander Pushkin";
    private static final String BOOKS_NAME = "book";
    private static final int BOOKS_COUNT_BY_AUTHOR = 1;
    private static final String BOOKS_GENR_NAME = "GENR_1";
    private static final String NO_NAME = "no name";
    public static final Book NEW_BOOK = new Book(null, "new book", new Author(1L,"Alexander Pushkin"),
            new HashSet<>(Arrays.asList(new Genr(2L, "GENR_2"))));


    @Autowired
    private BookDaoImpl bookDao;


    @Test
    @Order(1)
    @DisplayName(value = "get All books")
    void getAll() {
        List<Book> allBooks = bookDao.getAll();
        val books = allBooks.get(0);
        assertEquals(allBooks.size(),BOOKS_COUNT);
        assertThat(books).isNotNull()
                .matches(book -> book.getGenrs().size() == 1)
                .matches(book -> book.getName().equals(BOOKS_NAME))
                .matches(book -> book.getAuthor().getName().equals(BOOKS_AUTHOR_NAME));
        val books2 = allBooks.get(1);
        assertThat(books2).isNotNull()
                .matches(book -> book.getGenrs().size() == 2)
                .matches(book -> book.getName().equals("book2"))
                .matches(book -> book.getAuthor().getName().equals("Mikhail Bulgakov"));
    }
    @Test
    @Order(2)
    @DisplayName(value = "get book by ID")
    void getById() {
        val book = bookDao.getById(1);
        assertThat(book).isNotNull()
                .matches(b-> b.getId() == 1,"wrong book ID")
                .matches(b -> b.getAuthor().getName().equals(BOOKS_AUTHOR_NAME),"wrong author name")
                .matches(b-> b.getName().equals(BOOKS_NAME),"wrong book name")
                .matches(b-> b.getGenrs().stream().allMatch(g -> g.getName().equals(BOOKS_GENR_NAME)),"wrong genr name");

    }

    @Test
    @Order(3)
    @DisplayName(value = "get book by name")
    void getByName() {
        val book = bookDao.getByName("book");
        assertThat(book).isNotNull()
                .matches(b-> b.getId() == 1,"wrong book ID")
                .matches(b -> b.getAuthor().getName().equals(BOOKS_AUTHOR_NAME),"wrong author name")
                .matches(b-> b.getName().equals(BOOKS_NAME),"wrong book name")
                .matches(b-> b.getGenrs().stream().allMatch(g -> g.getName().equals(BOOKS_GENR_NAME)),"wrong genr name");

    }

    @Test
    @Order(4)
    @DisplayName(value = "get book list by author")
    void getByAuthor() {
        List<Book> byAuthor = bookDao.getByAuthor(BOOKS_AUTHOR_NAME);
        assertThat(byAuthor).isNotNull().hasSize(BOOKS_COUNT_BY_AUTHOR)
                .allMatch(b -> b.getName().equals(BOOKS_NAME),"wrong book name")
                .allMatch(b-> b.getId() == 1)
                .allMatch(b -> b.getAuthor().getName().equals(BOOKS_AUTHOR_NAME),"wrong author name")
                .allMatch(b-> b.getGenrs().stream().allMatch(g -> g.getName().equals("GENR_1")),"wrong genr name");


    }

    @Test
    @Order(5)
    @DisplayName(value = "get book by genr")
    void getByGenre() {
        List<Book> books = bookDao.getByGenre(BOOKS_GENR_NAME);
        assertThat(books).isNotNull().hasSize(2)
                .allMatch(b -> !b.getName().isEmpty())
                .allMatch(b -> b.getGenrs().stream()
                        .filter(g->g.getName().equals(BOOKS_GENR_NAME))
                        .allMatch(s-> s.getName().equals(BOOKS_GENR_NAME)));


    }

    @Test
    @Order(6)
    @DisplayName(value = "add new book with author and genr")
    void insert() {
        val book = bookDao.insert(NEW_BOOK);
        assertThat(book).isNotNull()
                .matches(b-> b.getId() == 4,"wrong book ID")
                .matches(b -> b.getAuthor().getName().equals(NEW_BOOK.getAuthor().getName()),"wrong author name")
                .matches(b-> b.getName().equals(NEW_BOOK.getName()),"wrong book name")
                .matches(b-> b.getGenrs().stream()
                        .allMatch(g -> g.getName()
                        .equals(NEW_BOOK.getGenrs()
                                .stream()
                                .findFirst()
                                .orElseThrow()
                                .getName())),"wrong genr name");
    }

    @Test
    @Order(7)
    @DisplayName(value = "remove book by ID")
    void deleteById() {
        bookDao.deleteById(4);
        List<Book> allBooks = bookDao.getAll();
        assertThat(allBooks).isNotNull().hasSize(BOOKS_COUNT);
    }

    @Test
    @DisplayName(value = "wrap EmptyResultDataAccessException in NotFoundException")
    void getByNameException() {
        ObjectFoundException thrown = assertThrows(ObjectFoundException.class, () -> bookDao.getByName(NO_NAME));
        assertTrue(thrown.getMessage().contains("not found"));
    }

    @Test
    @DisplayName(value = "throw new exception if author not present")
    void getByAuthorException() {
        ObjectFoundException thrown = assertThrows(ObjectFoundException.class, () -> bookDao.getByAuthor(NO_NAME));
        assertTrue(thrown.getMessage().contains("not found"));
    }

    @Test
    @DisplayName(value = "throw exception in method getByGenre(\"Жанр\") if genr is not in DB")
    void getByGenreException() {
        ObjectFoundException thrown = assertThrows(ObjectFoundException.class, () -> bookDao.getByGenre(NO_NAME));
        assertTrue(thrown.getMessage().contains("not found"));
    }

}