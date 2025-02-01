package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;
    private Validator validator;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void shouldAddFilmSuccessfully() {
        // Создаем фильм для добавления
        Film film = new Film();
        film.setName("Inception");
        film.setDescription("A mind-bending thriller");
        film.setReleaseDate(LocalDate.of(2010,7,16));
        film.setDuration(Duration.ofMinutes(148));
        // Добавляем фильм и проверяем результат
        ResponseEntity<Film> addedFilm = filmController.postFilm(film);
        assertNotNull(addedFilm.getBody().getId());
        assertEquals("Inception", addedFilm.getBody().getName());
        assertEquals(1, filmController.getFilms().size());
    }

    @Test
    void shouldFailWhenFilmNameIsBlank() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        Film film = new Film();
        film.setName(" ");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2022,1,1));
        film.setDuration(Duration.ofMinutes(120));
        // Проверяем, что метод postFilm выбросит ValidationException
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals("ОШИБКА! Некорректное имя фильма",
                violations.iterator().next().getMessage());
    }

    @Test
    void shouldGetAllFilms() {
        // Добавляем несколько фильмов
        Film film1 = new Film();
        film1.setName("Film 1");
        film1.setDescription("Description 1");
        film1.setReleaseDate(LocalDate.of(2020,1,1));
        film1.setDuration(Duration.ofMinutes(120));

        Film film2 = new Film();
        film2.setName("Film 2");
        film2.setDescription("Description 2");
        film2.setReleaseDate(LocalDate.of(2021,1,1));
        film2.setDuration(Duration.ofMinutes(90));

        filmController.postFilm(film1);
        filmController.postFilm(film2);

        // Получаем список всех фильмов и проверяем его содержимое
        Collection<Film> films = filmController.getFilms();
        assertEquals(2, films.size());
    }

    @Test
    void shouldPutFilmSuccessfully() {
        // Создаем фильм для добавления
        Film film = new Film();
        film.setName("Inception");
        film.setDescription("A mind-bending thriller");
        film.setReleaseDate(LocalDate.of(2010,7,16));
        film.setDuration(Duration.ofMinutes(148));
        // Добавляем фильм и проверяем результат
        ResponseEntity<Film> addedFilm = filmController.postFilm(film);
        assertNotNull(addedFilm.getBody().getId());
        assertEquals("Inception", addedFilm.getBody().getName());
        assertEquals(1, filmController.getFilms().size());
        film.setName("Blade runner 2049");
        ResponseEntity<Film> putFilm = filmController.putFilm(film);
        assertNotNull(addedFilm.getBody().getId());
        assertEquals("Blade runner 2049", addedFilm.getBody().getName());
        assertEquals(1, filmController.getFilms().size());
    }
}
