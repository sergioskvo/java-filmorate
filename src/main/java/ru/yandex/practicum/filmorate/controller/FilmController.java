package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Long, Film> filmsMap = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return filmsMap.values();
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) {
        log.info("Вводные данные:\n name: {}\n description: {}\n releaseDate: {}\n duration: {}",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Имя фильма пустое:\n name: {}", film.getName());
            throw new ValidationException("ОШИБКА! Некорректное имя фильма");
        }
        if (film.getDescription().length() > 200) {
            log.error("Описание длиннее 200 символов:\n description: {}\n description_length: {}",
                    film.getDescription(), film.getDescription().length());
            throw new ValidationException("ОШИБКА! Слишком длинное описание");
        }
        if (isReleaseDateBefore(film.getReleaseDate())) {
            log.error("Дата релиза фильма раньше 28 декабря 1895г:\n releaseDate: {}",
                    film.getReleaseDate());
            throw new ValidationException("ОШИБКА! Некорректная дата релиза");
        }
        if (film.getDuration().isNegative()) {
            log.error("Продолжительность фильма не может быть отрицательной:\n duration: {}",
                    film.getDuration());
            throw new ValidationException("ОШИБКА! Продолжительность фильма отрицательная");
        }
        film.setId(getNextId());
        filmsMap.put(film.getId(), film);
        log.info("Сохранен фильм:\n id: {}\n name: {}\n description: {}\n releaseDate: {}\n duration: {}",
                film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film newfilm) {
        log.info("Вводные данные:\n id: {}\n name: {}\n description: {}\n releaseDate: {}\n duration: {}",
                newfilm.getId(), newfilm.getName(), newfilm.getDescription(), newfilm.getReleaseDate(),
                newfilm.getDuration());
        if (newfilm.getId() == null) {
            log.error("Ошибка, некорректный id: {}", newfilm.getId());
            throw new ValidationException("Id должен быть указан");
        }
        if (filmsMap.containsKey(newfilm.getId())) {
            Film oldfilm = filmsMap.get(newfilm.getId());
            if (newfilm.getName() == null || newfilm.getName().isBlank()) {
                log.error("Имя фильма пустое:\n name: {}", newfilm.getName());
                throw new ValidationException("ОШИБКА! Некорректное имя фильма");
            }
            if (newfilm.getDescription().length() > 200) {
                log.error("Описание длиннее 200 символов:\n description: {}\n description_length: {}",
                        newfilm.getDescription(), newfilm.getDescription().length());
                throw new ValidationException("ОШИБКА! Слишком длинное описание");
            }
            if (isReleaseDateBefore(newfilm.getReleaseDate())) {
                log.error("Дата релиза фильма раньше 28 декабря 1895г:\n releaseDate: {}",
                        newfilm.getReleaseDate());
                throw new ValidationException("ОШИБКА! Некорректная дата релиза");
            }
            if (newfilm.getDuration().isNegative()) {
                log.error("Продолжительность фильма не может быть отрицательной:\n duration: {}",
                        newfilm.getDuration());
                throw new ValidationException("ОШИБКА! Продолжительность фильма отрицательная");
            }
            // если фильм найден и все условия соблюдены, обновляем её содержимое
            oldfilm.setName(newfilm.getName());
            oldfilm.setDescription(newfilm.getDescription());
            oldfilm.setReleaseDate(newfilm.getReleaseDate());
            oldfilm.setDuration(newfilm.getDuration());
            log.info("Обновленный фильм:\n name: {}\n description: {}\n releaseDate: {}\n duration: {}",
                    oldfilm.getName(), oldfilm.getDescription(), oldfilm.getReleaseDate(), oldfilm.getDuration());
            return oldfilm;
        }
        throw new ValidationException("Фильм с id = " + newfilm.getId() + " не найден");
    }

    private boolean isReleaseDateBefore(Instant releaseDate) {
        ZonedDateTime dec28_1895 = ZonedDateTime.of(1895, 12, 28, 0,
                0, 0, 0, ZoneOffset.UTC);
        Instant cutOffDate = dec28_1895.toInstant();
        return releaseDate.isBefore(cutOffDate);
    }

    private long getNextId() {
        long currentMaxId = filmsMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
