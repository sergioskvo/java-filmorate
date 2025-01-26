package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        // Создаем новый экземпляр UserController перед каждым тестом
        userController = new UserController();
    }

    @Test
    void shouldAddUserSuccessfully() {
        // Создаем пользователя для добавления
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1,1));
        // Добавляем пользователя и проверяем результат
        User addedUser = userController.postUser(user);
        assertNotNull(addedUser.getId());
        assertEquals("test@example.com", addedUser.getEmail());
        assertEquals(1, userController.getUsers().size());
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1,1));
        // Проверяем, что метод postUser выбросит ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () -> userController.postUser(user));
        assertEquals("ОШИБКА! Некорректный email", exception.getMessage());
    }

    @Test
    void shouldGetAllUsers() {
        // Добавляем несколько пользователей
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setLogin("user1");
        user1.setName("User One");
        user1.setBirthday(LocalDate.of(1980, 1,1));
        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setLogin("user2");
        user2.setName("User Two");
        user2.setBirthday(LocalDate.of(1990, 1,1));
        userController.postUser(user1);
        userController.postUser(user2);
        // Получаем список всех пользователей и проверяем его содержимое
        Collection<User> users = userController.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    void shouldPutUserSuccessfully() {
        // Создаем пользователя для добавления
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1,1));
        // Добавляем пользователя и проверяем результат
        User addedUser = userController.postUser(user);
        assertNotNull(addedUser.getId());
        assertEquals("test@example.com", addedUser.getEmail());
        assertEquals(1, userController.getUsers().size());
        user.setEmail("newtest@example.com");
        assertEquals("newtest@example.com", addedUser.getEmail());
        assertEquals(1, userController.getUsers().size());
    }
}
