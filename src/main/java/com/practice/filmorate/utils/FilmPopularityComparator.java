package com.practice.filmorate.utils;

import com.practice.filmorate.model.Film;

import java.util.Comparator;

public class FilmPopularityComparator implements Comparator<Film> {
    @Override
    public int compare(Film film1, Film film2) {
        return film2.getNumOfLikes() - film1.getNumOfLikes();
    }
}
