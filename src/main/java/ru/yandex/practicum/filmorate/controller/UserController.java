package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> userMap = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return userMap.values();
    }

    @PostMapping
    public User postUser(@RequestBody User user) {
        log.info("Вводные данные:\n email: {}\n login: {}\n name: {}\n birthday: {}",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Email не удовлетворяет требованиям:\n email: {}", user.getEmail());
            throw new ValidationException("ОШИБКА! Некорректный email");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Login не удовлетворяет требованиям:\n login: {}", user.getLogin());
            throw new ValidationException("ОШИБКА! Некорректный login");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Имя не может быть пустым, поэтому будет использован логин в качестве имени");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(Instant.now())) {
            log.error("Дата рождения не может быть в будущем:\n birthday: {}", user.getBirthday());
            throw new ValidationException("ОШИБКА! Некорректный birthday");
        }
        user.setId(getNextId());
        userMap.put(user.getId(), user);
        log.info("Сохранен пользователь:\n id: {}\n email: {}\n login: {}\n name: {}\n birthday: {}",
                user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User newUser) {
        log.info("Вводные данные:\n id: {}\n email: {}\n login: {}\n name: {}\n birthday: {}",
                newUser.getId(), newUser.getEmail(), newUser.getLogin(), newUser.getName(),
                newUser.getBirthday());
        if (newUser.getId() == null) {
            log.error("Ошибка, некорректный id: {}", newUser.getId());
            throw new ValidationException("Id должен быть указан");
        }
        if (userMap.containsKey(newUser.getId())) {
            User oldUser = userMap.get(newUser.getId());
            if (newUser.getEmail() == null || newUser.getEmail().isBlank() || !newUser.getEmail().contains("@")) {
                log.error("Email не удовлетворяет требованиям:\n email: {}", newUser.getEmail());
                throw new ValidationException("ОШИБКА! Некорректный email");
            }
            if (newUser.getLogin() == null || newUser.getLogin().isBlank() || newUser.getLogin().contains(" ")) {
                log.error("Login не удовлетворяет требованиям:\n login: {}", newUser.getLogin());
                throw new ValidationException("ОШИБКА! Некорректный login");
            }
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                log.info("Имя не может быть пустым, поэтому будет использован логин в качестве имени");
                newUser.setName(newUser.getLogin());
            }
            if (newUser.getBirthday().isAfter(Instant.now())) {
                log.error("Дата рождения не может быть в будущем:\n birthday: {}", newUser.getBirthday());
                throw new ValidationException("ОШИБКА! Некорректный birthday");
            }
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            log.info("Обновлен пользователь:\n id: {}\n email: {}\n login: {}\n name: {}\n birthday: {}",
                    oldUser.getId(), oldUser.getEmail(), oldUser.getLogin(), oldUser.getName(), oldUser.getBirthday());
            return oldUser;
        }
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = userMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }


}
