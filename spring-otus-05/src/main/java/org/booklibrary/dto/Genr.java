package org.booklibrary.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Genr {

    private Long id;
    private String name;

    public Genr(String name) {
        this(null, name);
    }

    public Genr(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, '%s')", getClass().getSimpleName(), id, name);
    }
}
