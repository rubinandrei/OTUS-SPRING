package org.booklibrary.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Book {

    private Long id;
    private String name;
    private Author author;
    private Set<Genr> genrs;

    public Book(Long id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Book(Long id, String name, Author author, Set<Genr> genrs) {
        this(id, name, author);
        this.genrs = genrs;
    }

    public Set<Genr> getGenrs() {
        return genrs != null ? genrs : new HashSet<>();
    }

    @Override
    public String toString() {
        return "Book{(id=" + id + ") ' " + name + " '" +
                ", author: " + author.getName() +
                ", genres: " + getGenrs().stream().map(Genr::getName).collect(Collectors.toList()) +
                '}';
    }
}
