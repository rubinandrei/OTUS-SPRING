package org.booklibrary.dao;

import lombok.extern.slf4j.Slf4j;
import org.booklibrary.dto.Genr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(GenreDaoImpl.class)
@DisplayName(value = "DAO genr")
@Slf4j
class GenrDaoImplTest {

    private final Genr NEW_GENR = new Genr(null, "New Genre");

    @Autowired
    GenreDaoImpl genreDao;

    @Test
    @DisplayName(value = "get all genr")
    void getAll() {
        List<Genr> allGenrs = genreDao.getAll();
        assertThat(allGenrs).hasSize(genreDao.count());
    }

    @Test
    @DisplayName(value = "get genr by name")
    void getByName() {
        Genr genr = genreDao.getByName("GENR_1");
        assertThat(genr).isNotNull()
                .matches(s->s.getName().equals("GENR_1"))
                .matches(s->s.getId() == 1);
    }

    @Test
    @DisplayName(value = "insert genr")
    void insert() {
        int expectedCount = genreDao.count() + 1;
        genreDao.insert(NEW_GENR);
        assertEquals(expectedCount, genreDao.count());
    }
}