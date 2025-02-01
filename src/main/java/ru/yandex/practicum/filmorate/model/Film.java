package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Schema(description = "Фильм")
public class Film {
    @Schema(description = "Id фильма", example = "1")
    private Long id;
    @Schema(description = "Имя фильма", example = "Марсианин")
    @NotBlank(message = "ОШИБКА! Некорректное имя фильма")
    private String name;
    @Schema(description = "Описание фильма", example = "Фильм об астронавте, который остался один на Марсе и выживает")
    @Size(min = 1, max = 200)
    private String description;
    @Schema(description = "Дата релиза фильма", example = "2015-09-11")
    private LocalDate releaseDate;
    @Schema(description = "Продолжительность фильма", example = "P0DT2H22M")
    private Duration duration;

    @JsonGetter("duration")
    public long getDurationInSeconds() {
        return duration != null ? duration.getSeconds() : 0; // возвращаем количество секунд
    }
}
