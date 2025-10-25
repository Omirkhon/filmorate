package com.practice.filmorate;

import com.practice.filmorate.controller.FilmController;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.storage.impl.FilmDbStorage;
import com.practice.filmorate.storage.impl.UserDbStorage;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//public class FilmControllerTest {
//    FilmController filmController = new FilmController(new FilmService(new FilmDbStorage(), new UserDbStorage()));
//    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//
//    protected String validateAndGetFirstMessageTemplate(Film film) {
//        return validator.validate(film).stream()
//                .findFirst()
//                .orElseThrow()
//                .getConstraintDescriptor()
//                .getMessageTemplate();
//    }
//
//    @Test
//    public void shouldNotValidateFilmWhenNameIsBlank() {
//        Film film = new Film(1,"", "Описание", LocalDate.of(2018, 1, 1), 2, new Mpa(1, "a", "a"));
//
//        String expected = "Название не может быть пустым.";
//        String actual = validateAndGetFirstMessageTemplate(film);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void shouldNotValidateFilmWhenDescriptionIsTooLong() {
//        Film film = new Film(2,"АА", "A".repeat(201), LocalDate.of(2018, 1, 1), 2, new Mpa(1, "a", "a"));
//
//        String expected = "Слишком длинное описание.";
//        String actual = validateAndGetFirstMessageTemplate(film);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void shouldNotValidateWhenDateIsIncorrect() {
//        Film film = new Film(1,"АА", "Описание", LocalDate.of(1895, 1, 1), 2, new Mpa(1, "a", "a"));
//
//        String expected = "Некорректная дата релиза.";
//        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
//
//        assertEquals(expected, exception.getMessage());
//    }
//
//    @Test
//    public void shouldNotValidateWhenDurationIsNegative() {
//        Film film = new Film(1,"АА", "Описание", LocalDate.of(1895, 12, 30), -1, new Mpa(1, "a", "a"));
//
//        String expected = "Некорректная продолжительность фильма.";
//        String actual = validateAndGetFirstMessageTemplate(film);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void shouldValidateWhenAllCorrect() {
//        Film film = new Film(1, "АА", "Описание", LocalDate.of(1895, 12, 30), 2, new Mpa(1, "a", "a"));
//
//        assertEquals(film, filmController.addFilm(film));
//    }
//}
