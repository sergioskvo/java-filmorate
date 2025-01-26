package ru.yandex.practicum.filmorate.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Schema(description = "Фильм")
public class Film {
    @Schema(description = "Id фильма", example = "1")
    Long id;
    @Schema(description = "Имя фильма", example = "Марсианин")
    String name;
    @Schema(description = "Описание фильма", example = "Фильм об астронавте, который остался один на Марсе и выживает")
    String description;
    @Schema(description = "Дата релиза фильма", example = "2015-09-11")
    LocalDate releaseDate;
    @Schema(description = "Продолжительность фильма", example = "P0DT2H22M")
    Duration duration;
}
