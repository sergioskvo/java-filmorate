package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;

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
        film.setReleaseDate(Instant.parse("2010-07-16T00:00:00Z"));
        film.setDuration(Duration.ofMinutes(148));
        // Добавляем фильм и проверяем результат
        Film addedFilm = filmController.postFilm(film);
        assertNotNull(addedFilm.getId());
        assertEquals("Inception", addedFilm.getName());
        assertEquals(1, filmController.getFilms().size());
    }

    @Test
    void shouldFailWhenFilmNameIsBlank() {
        Film film = new Film();
        film.setName(" ");
        film.setDescription("Description");
        film.setReleaseDate(Instant.parse("2022-01-01T00:00:00Z"));
        film.setDuration(Duration.ofMinutes(120));
        // Проверяем, что метод postFilm выбросит ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.postFilm(film));
        assertEquals("ОШИБКА! Некорректное имя фильма", exception.getMessage());
    }

    @Test
    void shouldGetAllFilms() {
        // Добавляем несколько фильмов
        Film film1 = new Film();
        film1.setName("Film 1");
        film1.setDescription("Description 1");
        film1.setReleaseDate(Instant.parse("2020-01-01T00:00:00Z"));
        film1.setDuration(Duration.ofMinutes(120));

        Film film2 = new Film();
        film2.setName("Film 2");
        film2.setDescription("Description 2");
        film2.setReleaseDate(Instant.parse("2021-01-01T00:00:00Z"));
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
        film.setReleaseDate(Instant.parse("2010-07-16T00:00:00Z"));
        film.setDuration(Duration.ofMinutes(148));
        // Добавляем фильм и проверяем результат
        Film addedFilm = filmController.postFilm(film);
        assertNotNull(addedFilm.getId());
        assertEquals("Inception", addedFilm.getName());
        assertEquals(1, filmController.getFilms().size());
        film.setName("Blade runner 2049");
        Film putFilm = filmController.putFilm(film);
        assertNotNull(addedFilm.getId());
        assertEquals("Blade runner 2049", addedFilm.getName());
        assertEquals(1, filmController.getFilms().size());
    }
}
