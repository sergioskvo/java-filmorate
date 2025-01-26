package ru.yandex.practicum.filmorate.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

/**
 * Film.
 */
@Data
@Schema(description = "Фильм")
public class Film {
    @Schema(description = "Id фильма", example = "1")
    Long id;
    @Schema(description = "Имя фильма", example = "Марсианин")
    String name;
    @Schema(description = "Описание фильма", example = "Фильм об астронавте, который остался один на Марсе и выживает")
    String description;
    @Schema(description = "Дата релиза фильма", example = "2015-09-11T10:00:00Z")
    Instant releaseDate;
    @Schema(description = "Продолжительность фильма", example = "P0DT2H22M")
    Duration duration;
}
