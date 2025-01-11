package com.practice.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre implements Comparable<Genre> {
    @EqualsAndHashCode.Include
    int id;
    String name;

    @Override
    public int compareTo(Genre o) {
        return Integer.compare(this.id, o.id);
    }
}
