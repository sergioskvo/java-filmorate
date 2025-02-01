package ru.yandex.practicum.filmorate.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Пользователь приложения")
public class User {
    @Schema(description = "Id пользователя", example = "1")
    private Long id;
    @Schema(description = "Email пользователя", example = "sergiskvor@yandex.ru")
    private String email;
    @Schema(description = "Логин пользователя", example = "serg")
    private String login;
    @Schema(description = "Имя пользователя", example = "sergei")
    private String name;
    @Schema(description = "Дата рождения пользователя", example = "1998-02-11")
    private LocalDate birthday;
}