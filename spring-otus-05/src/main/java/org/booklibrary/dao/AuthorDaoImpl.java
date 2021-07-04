package org.booklibrary.dao;

import org.booklibrary.dto.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private static final BeanPropertyRowMapper<Author> AUTHOR_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Author.class);

    private final NamedParameterJdbcOperations jdbcOperations;
    private final SimpleJdbcInsert jdbcInsert;

    public AuthorDaoImpl(DataSource dataSource, NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("authors")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer count() {
        Map<String, Object> params = Collections.emptyMap();
        return jdbcOperations.queryForObject("select count(*) from AUTHORS", params, Integer.class);
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Author author;
        try {
            author = jdbcOperations.queryForObject(
                    "select author.id, author.name from AUTHORS author" +
                            "               where author.name= :name"
                    , params, AUTHOR_ROW_MAPPER
            );
            return author;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Author insert(Author author) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(author);
        Number newKey = jdbcInsert.executeAndReturnKey(parameterSource);
        author.setId(newKey.longValue());
        return author;
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("SELECT author.id, author.name FROM AUTHORS", AUTHOR_ROW_MAPPER);
    }
}
