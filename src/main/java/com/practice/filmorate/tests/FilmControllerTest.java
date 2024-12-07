package com.practice.filmorate.tests;

import com.practice.filmorate.controller.FilmController;
import com.practice.filmorate.model.Film;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    FilmController filmController = new FilmController();
    @Test
    public void shouldNotValidateFilmWhenNameIsBlank() {
        Film film = new Film(1, "", "Описание", LocalDate.of(2018, 1, 1), Duration.ofHours(2));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmController.addFilm(film));
        assertEquals("Название не может быть пустым.", exception.getMessage());
    }

    @Test
    public void shouldNotValidateFilmWhenDescriptionIsTooLong() {
        Film film = new Film(1, "АА", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA.", LocalDate.of(2018, 1, 1), Duration.ofHours(2));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmController.addFilm(film));
        assertEquals("Слишком длинное описание.", exception.getMessage());
    }

    @Test
    public void shouldNotValidateWhenDateIsIncorrect() {
        Film film = new Film(1, "АА", "Описание", LocalDate.of(1895, 1, 1), Duration.ofHours(2));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmController.addFilm(film));
        assertEquals("Некорректная дата релиза.", exception.getMessage());
    }

    @Test
    public void shouldNotValidateWhenDurationIsNegative() {
        Film film = new Film(1, "АА", "Описание", LocalDate.of(1895, 12, 30), Duration.ofHours(-1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmController.addFilm(film));
        assertEquals("Некорректная продолжительность фильма.", exception.getMessage());
    }

    @Test
    public void shouldValidateWhenAllCorrect() {
        Film film = new Film(1, "АА", "Описание", LocalDate.of(1895, 12, 30), Duration.ofHours(2));

        assertEquals(film, filmController.addFilm(film));
    }
}
