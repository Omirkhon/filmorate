package com.practice.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Genre {
    int id;
    String name;
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
