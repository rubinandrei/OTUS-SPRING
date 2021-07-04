package org.booklibrary.dao;

import org.booklibrary.dto.Author;
import org.booklibrary.dto.Book;
import org.booklibrary.dto.Genr;
import org.booklibrary.exception.ObjectFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookDaoImpl implements BookDao {

    private static final BeanPropertyRowMapper<Genr> GENRE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Genr.class);

    private final NamedParameterJdbcOperations jdbcOperations;
    private final SimpleJdbcInsert jdbcInsert;

    public BookDaoImpl(DataSource dataSource, NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("books")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer count() {
        Map<String, Object> params = Collections.emptyMap();
        return jdbcOperations.queryForObject("select count(*) from BOOKS", params, Integer.class);
    }

    @Override
    public Book getById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book;
        try {
            book = jdbcOperations.queryForObject(
                    "select book.id, book.name, book.author_id, " +
                            "  author.id , author.name as author_name from books book " +
                            "left outer join authors author " +
                            "  on book.author_id=author.id " +
                            "where book.id= :id"
                    , params, new BookMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectFoundException(String.format("Book with id '%s' not found.", id));
        }
        return setGenre(book);
    }

    @Override
    public Book getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Book book;
        try {
            book = jdbcOperations.queryForObject(
                    "select book.id, book.name, book.author_id, " +
                            "  author.id , author.name as author_name from books book " +
                            "inner join authors author " +
                            "on BOOK.AUTHOR_ID = AUTHOR.ID " +
                            "where BOOK.NAME = :name limit 1"
                    , params, new BookMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectFoundException(String.format("Book with name '%s' not found.", name));
        }
        return setGenre(book);
    }

    @Override
    public List<Book> getByAuthor(String author) {
        Map<String, Object> params = Collections.singletonMap("author", author);
        List<Book> books;
        books = jdbcOperations.query(
                "select book.id, book.name, book.author_id, " +
                        "  author.id , author.name as author_name from AUTHORS author " +
                        "left outer join BOOKS book " +
                        "  on BOOK.AUTHOR_ID = AUTHOR.ID " +
                        "where AUTHOR.NAME = :author"
                , params, new RowMapperResultSetExtractor<>(new BookMapper())
        );
        if (books.size() <= 0) {
            throw new ObjectFoundException(String.format(
                    "Book with author '%s' not found. Perhaps this author does not exist in our library.", author));
        }
        for (Book book : books) {
            setGenre(book);
        }
        return books;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        Map<String, Object> params = Collections.singletonMap("genre", genre);
        List<Book> books = queryBooks(params);
        if (books.size() <= 0) {
            throw new ObjectFoundException(String.format(
                    "Book with genre '%s' not found. Perhaps this genre does not exist in our library.", genre));
        }
        for (Book book : books) {
            setGenre(book);
        }
        return books;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = (List<Book>) jdbcOperations.query("SELECT book.id, book.name, book.author_id, " +
                "author.name as author_name,g.id as genr_id, g.name as genr_name   FROM BOOKS as book" +
                " left outer join authors author " +
                "  on book.author_id=author.id " +
                " left outer join book_genr g_b " +
                "  on book.id = g_b.book_id " +
                " left outer join genr g " +
                "  on g_b.genre_id = g.id  ", new BookAllMapper());
        return books;
    }

    @Override
    public Book insert(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(
                "authorId", book.getAuthor().getId())
                .addValue("name", book.getName());
        Number newKey = jdbcInsert.executeAndReturnKey(parameterSource);
        book.setId(newKey.longValue());
        insertGenres(book);
        return book;
    }

    @Override
    public int update(String bookName, String newName) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("new_name", newName)
                .addValue( "name", bookName);
        return jdbcOperations.update("UPDATE BOOKS set BOOKS.NAME = :new_name where BOOKS.NAME = :name",params);

    }

    @Override
    public boolean deleteById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update("DELETE FROM book_genr WHERE book_id=:id", params);
        return jdbcOperations.update("delete from BOOKS where id = :id", params) != 0;
    }

    private List<Book> queryBooks(Map<String, Object> params) {
        return jdbcOperations.query(
                "SELECT b.id as book_id, B.NAME as book_name, b.AUTHOR_ID, a.NAME as author_name " +
                        "FROM BOOKS b " +
                        "         join book_genr on book_genr.BOOK_ID = B.ID " +
                        "         join GENR g on book_genr.GENRE_ID = g.ID " +
                        "         join AUTHORS a on b.AUTHOR_ID = a.ID " +
                        "where g.name = :genre"
                , params, new BookMapper());
    }

    private Map<Long, Set<Genr>> getAllGenresWithBookId() {
        Map<Long, Set<Genr>> allGenresByBookId = new HashMap<>();
        jdbcOperations.query("SELECT book_genr.book_id as book_id, book_genr.genre_id as genre_id, g.name as name  FROM book_genr " +
                "join GENR g on book_genr.GENRE_ID = g.ID ORDER BY BOOK_ID, GENRE_ID", rs -> {
            allGenresByBookId.compute(rs.getLong("book_id"), (key, value) -> {
                if (value == null) {
                    value = new HashSet<>();
                }
                try {
                    value.add(new Genr(rs.getLong("genre_id"), rs.getString("name")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return value;
            });
        });
        return allGenresByBookId;
    }

    private Book setGenre(Book book) {
        if (book != null) {
            Map<String, Object> params = Collections.singletonMap("book_id", book.getId());
            List<Genr> genrs = jdbcOperations.query(
                    "select genr.* from books book " +
                            "LEFT join book_genr link on book.id=link.BOOK_ID " +
                            "LEFT join genr on link.GENRE_ID=genr.id " +
                            "where book.id= :book_id",
                    params, GENRE_ROW_MAPPER);
            book.setGenrs(new HashSet<>(genrs));
        }
        return book;
    }

    @SuppressWarnings("unchecked")
    private void insertGenres(Book book) {
        List<Genr> genrs = new ArrayList<>(book.getGenrs());
        if (!CollectionUtils.isEmpty(genrs)) {
            List<Map<String, Object>> parameters = new ArrayList<>();
            for (Genr genr : genrs) {
                parameters.add(new HashMap<String, Object>() {{
                    put("bookId", book.getId());
                    put("genreId", genr.getId());
                }});
            }
            Map<String, Object>[] parameterSources = parameters.toArray(new HashMap[0]);
            jdbcOperations.batchUpdate("INSERT INTO book_genr (BOOK_ID, GENRE_ID) VALUES (:bookId, :genreId)", parameterSources);
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getInt("id");
            String name = rs.getString("name");
            long authorId = rs.getInt("author_id");
            String authorName = rs.getString("author_name");
            return new Book(id, name, new Author(authorId, authorName));
        }
    }

    private static class BookAllMapper implements ResultSetExtractor {
        Map<Long, Book> bookById = new HashMap<>();
        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
                long id = rs.getInt("id");
                String name = rs.getString("name");
                long authorId = rs.getInt("author_id");
                String authorName = rs.getString("author_name");
                long genrId = rs.getInt("genr_id");
                String genrName = rs.getString("genr_name");
                Book book = bookById.get(id);
                if (book == null) {
                    Set<Genr> genr = new HashSet<>();
                    book = new Book(id, name, new Author(authorId, authorName), genr);
                    bookById.put(book.getId(), book);
                }
                if (!Objects.isNull(genrId)) {
                    book.getGenrs().add(new Genr(genrId, genrName));
                }
            }
            return bookById.values().stream().collect(Collectors.toList());
        }

    }
}
