package ru.yandex.practicum.filmorate.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "Пользователь приложения")
public class User {
    @Schema(description = "Id пользователя", example = "1")
    Long id;
    @Schema(description = "Email пользователя", example = "sergiskvor@yandex.ru")
    String email;
    @Schema(description = "Логин пользователя", example = "serg")
    String login;
    @Schema(description = "Имя пользователя", example = "sergei")
    String name;
    @Schema(description = "Дата рождения пользователя", example = "1998-02-11T10:00:00Z")
    Instant birthday;
}