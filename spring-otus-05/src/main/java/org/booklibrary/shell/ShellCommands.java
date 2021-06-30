package org.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.booklibrary.dao.AuthorDao;
import org.booklibrary.dao.GenreDao;
import org.booklibrary.dto.Author;
import org.booklibrary.dto.Book;
import org.booklibrary.dto.Genr;
import org.booklibrary.exception.ObjectFoundException;
import org.booklibrary.services.BookService;
import org.booklibrary.services.IOService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellCommands {

    private final IOService ioService;
    private final BookService bookService;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    private Book newBook;

    @ShellMethod(value = "Get list all books", key = "books-all")
    public void getAllBooks() {
        List<Book> books = bookService.getAll();
        for (Book book : books) {
            ioService.print(book.toString());
        }
    }

    @ShellMethod(value = "Get book by id", key = "book")
    public void getBookById(int id) {
        try {
            ioService.print(bookService.getById(id).toString());
        } catch (Exception e) {
            ioService.print(e.getMessage());
        }
    }

    @ShellMethod(value = "Get list books by author", key = "books-by-author")
    public void getBooksByAuthor(String author) {
        author = replaceUnderscores(author);
        try {
            List<Book> books = bookService.getByAuthor(author);
            for (Book book : books) {
                ioService.print(book.toString());
            }
        } catch (ObjectFoundException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Get list books by genre", key = "books-by-genr")
    public void getBooksByGenre(String genre) {
        genre = replaceUnderscores(genre);
        try {
            List<Book> books = bookService.getByGenre(genre);
            for (Book book : books) {
                ioService.print(book.toString());
            }
        } catch (ObjectFoundException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = "update book name", key = "update-book-name")
    public void UpdateBooksByName(@ShellOption(value = "--name") String name, @ShellOption("--new-name") String newName) {
        name = replaceUnderscores(name);
        newName = replaceUnderscores(newName);
        try {
            Book book = bookService.updateBook(name, newName);
            ioService.print(book.toString());
        } catch (ObjectFoundException e) {
            ioService.print(String.format("book %s not found to update", name));
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Get list all genres", key = "all-genres")
    public void getAllGenres() {
        List<Genr> genrs = genreDao.getAll();
        for (Genr genr : genrs) {
            ioService.print(genr.toString());
        }
    }

    @ShellMethod(value = "Get list all author", key = "authors")
    public void getAllAuthors() {
        List<Author> author = bookService.getAllAuthor();
        ioService.print(Arrays.deepToString(author.toArray()));

    }

    @ShellMethod(value = "Create new book command (if name/author/genre contains spaces, replace it to underscores ('_'))"
            , key = "add-book")
    public void addBook(@ShellOption(value = "--name") String name, @ShellOption("--author") String authorName,
                        @ShellOption(value = "--genre") String genreName) {
        name = replaceUnderscores(name);
        authorName = replaceUnderscores(authorName);
        genreName = replaceUnderscores(genreName);
        try {
            Book book = bookService.getByName(name);
            ioService.print(String.format("The %s exists.", book.toString()));
        } catch (ObjectFoundException ex) {
            log.error(ex.getMessage());
        }
        Author author = authorDao.getByName(authorName);
        Genr genr = genreDao.getByName(genreName);
        if (author == null) {
            ioService.print(String.format("Author with name '%s' is not exists. A new one will be created.", authorName));
            author = new Author(authorName);
        }
        if (genr == null) {
            ioService.print(String.format("Genre with name '%s' is not exists. A new one will be created.", genreName));
            genr = new Genr(authorName);
        }
        newBook = new Book(null, name, author, new HashSet<>(Collections.singleton(genr)));
        ioService.print("Now you can add genres to created new book and/or save/deny creation");
    }

    @ShellMethod(value = "Add genre to new book", key = "genre-add-to-new-book")
    public void addGenreToNewBook(String genreName) {
        if (!Objects.isNull(newBook)) {
            genreName = replaceUnderscores(genreName);
            Genr genr = genreDao.getByName(genreName);
            if (genr == null) {
                ioService.print(String.format("Genre with name '%s' is not exists. A new one will be created.", genreName));
                genr = new Genr(genreName);
            }
            newBook.getGenrs().add(genr);
            ioService.print(String.format("%s ready to save.", newBook));
        } else {
            ioService.print("Please ann new book at the first");
        }
    }

    @ShellMethod(value = "Delete book by ID", key = "book-delete")
    public void deleteBookById(int id) {
        if (bookService.deleteById(id)) {
            ioService.print(String.format("book with id '%s' deleted.", id));
        }
    }

    private String replaceUnderscores(String option) {
        return option.replace('_', ' ');
    }

    @ShellMethod(value = "Save creation command", key = "save-book")
    public void saveCreation() {
        bookService.save(newBook);
        ioService.print(String.format("Book %s saved", newBook));
        newBook = null;
    }

    @ShellMethod(value = "exit app", key = "exit-app")
    public void exitApp() {
        System.exit(0);
    }

    @ShellMethod(value = "Deny creation command", key = "deny-book")
    public void denyCreation() {
        newBook = null;
    }

    private Availability getAllBooksAvailability() {
        return creationAvailability();
    }

    private Availability getBookByIdAvailability() {
        return creationAvailability();
    }

    private Availability getBooksByAuthorAvailability() {
        return creationAvailability();
    }

    private Availability getBooksByGenreAvailability() {
        return creationAvailability();
    }

    private Availability deleteBookByIdAvailability() {
        return creationAvailability();
    }

    private Availability addGenreToNewBookAvailability() {
        return saveOrDenyAvailability();
    }

    private Availability saveCreationAvailability() {
        return saveOrDenyAvailability();
    }

    private Availability denyCreationAvailability() {
        return saveOrDenyAvailability();
    }

    private Availability creationAvailability() {
        if (newBook != null) {
            return Availability.unavailable(String.format("\n Now you creating the book %s. \nConfirm or deny creation by command " +
                    "\n Confirm: 'save-book'. \n Deny: 'deny-book'", newBook.getName()));
        }
        return Availability.available();
    }

    private Availability saveOrDenyAvailability() {
        if (newBook == null) {
            return Availability.unavailable("\n This command for save or deny created book." +
                    "\nFor create new book use command 'add-book' ");
        }
        return Availability.available();
    }
}
