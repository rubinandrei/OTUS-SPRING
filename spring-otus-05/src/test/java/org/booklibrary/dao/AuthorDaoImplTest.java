package org.booklibrary.dao;

import lombok.extern.slf4j.Slf4j;
import org.booklibrary.dto.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(AuthorDaoImpl.class)
@DisplayName(value = "DAO authors")
@Slf4j
@TestPropertySource(properties = {"spring.datasource.schema=classpath:test-schema.sql"})
class AuthorDaoImplTest {

    private static final int AUTHOR_COUNT = 3;
    private static final String BOOKS_AUTHOT_NAME = "Ilya Petrov";
    public static final List<Author> AUTHOR_LIST =
            Arrays.asList(new Author("Author1"), new Author("Author2"));


    @Autowired
    AuthorDaoImpl authorDao;

    @Test
    @DisplayName(value = "get author by name")
    void getAuthorByNameTest() {
        Author author = authorDao.getByName(BOOKS_AUTHOT_NAME);
        assertEquals(author.getId(), 3);
    }

    @Test
    @DisplayName(value = "save new Author")
    void inserAuthorTest() {
        Author author = authorDao.insert(AUTHOR_LIST.get(0));
        assertThat(author).isNotNull().matches(s -> s.getName().equals(AUTHOR_LIST.get(0).getName()));
        assertThat(authorDao.getAll()).isNotNull().hasSize(AUTHOR_COUNT+1);
    }
}