package org.booklibrary.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.booklibrary.dao.AuthorDao;
import org.booklibrary.dao.GenreDao;
import org.booklibrary.dto.Author;
import org.booklibrary.dto.Book;
import org.booklibrary.dto.Genr;
import org.booklibrary.services.BookService;
import org.booklibrary.services.BookServiceImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.booklibrary.dao.BookDao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookServiceImpl.class)
@DisplayName(value = "book service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@RunWith(SpringRunner.class)
class BookServiceTest {

    public static final List<Author> AUTHOR_LIST =
            Arrays.asList(new Author(1L, "Author1"), new Author(2L, "Author2"));

    public static final List<Genr> GENR_LIST =
            Arrays.asList(new Genr(1L, "Genr1"), new Genr(2L, "Genr2"));

    public final List<Book> NEW_BOOK = Arrays.asList(
            new Book(1L, "book1", AUTHOR_LIST.get(0), new HashSet<>(GENR_LIST)),
            new Book(2L, "book2", AUTHOR_LIST.get(1), new HashSet<>(GENR_LIST))
    );

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private BookService bookService;

    @Before
    public void init() {
        System.out.println("BookServiceTest.init");
    }

    @Test
    @DisplayName(value = "get book")
    void getBookByNameTest() {
        Mockito.when(bookDao.getByName("TestName")).thenReturn(NEW_BOOK.get(0));
        val books = bookService.getByName("TestName");
        assertThat(books.getId()).isEqualTo(NEW_BOOK.get(0).getId());
    }

    @Test
    @DisplayName(value = "get book by id")
    void getBookbyIdTest() {
        Mockito.when(bookDao.getById(2)).thenReturn(NEW_BOOK.get(1));
        val books = bookService.getById(2);
        assertThat(books.getName()).isEqualTo(NEW_BOOK.get(1).getName());
    }

    @Test
    @DisplayName(value = "update book")
    void updateBookName() {
        Mockito.when(bookDao.update(NEW_BOOK.get(0).getName(), "newName")).thenReturn(1);
        Mockito.when(bookDao.getByName("newName")).thenReturn(NEW_BOOK.get(1));
        val books = bookService.updateBook(NEW_BOOK.get(0).getName(), "newName");
        assertThat(books.getName()).isEqualTo((NEW_BOOK.get(1).getName()));
    }


}