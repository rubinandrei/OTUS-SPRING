package org.booklibrary.dao;

import org.booklibrary.dto.Genr;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private static final BeanPropertyRowMapper<Genr> GENRE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Genr.class);

    private final NamedParameterJdbcOperations jdbcOperations;
    private final SimpleJdbcInsert jdbcInsert;

    public GenreDaoImpl(DataSource dataSource, NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("genr")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer count() {
        Map<String, Object> params = Collections.emptyMap();
        return jdbcOperations.queryForObject("select count(*) from GENR", params, Integer.class);
    }

    @Override
    public Genr getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Genr genr;
        try {
            genr = jdbcOperations.queryForObject(
                    "select genr.id, genr.name from genr" +
                            "               where genr.name= :name"
                    , params, GENRE_ROW_MAPPER
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return genr;
    }

    @Override
    public List<Genr> getAll() {
        return jdbcOperations.query("SELECT * FROM GENR", GENRE_ROW_MAPPER);
    }

    @Override
    public Genr insert(Genr genr) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(genr);
        Number newKey = jdbcInsert.executeAndReturnKey(parameterSource);
        genr.setId(newKey.longValue());
        return genr;
    }
}
