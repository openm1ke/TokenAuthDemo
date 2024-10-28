package tokenbased;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tokenbased.models.User;
import tokenbased.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@Transactional
class DatabaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Этот метод будет выполняться перед каждым тестом
        //userRepository.deleteAll();
    }

    @Test
    void testDatabaseCreationAndInitialData() {
        // Проверим, что данные из data.sql загружены корректно
        log.info("start testDatabaseCreationAndInitialData");
        List<User> users = userRepository.findAll();
        log.info("users = " + users);
        assertEquals(3, users.size(), "В базе данных должно быть 3 пользователя");

        Optional<User> user1 = userRepository.findByLogin("user1");
        log.info("user1 = " + user1);
        assertTrue(user1.isPresent(), "Пользователь с логином user1 должен быть в базе данных");
        assertEquals("password1", user1.get().getPassword(), "Пароль пользователя user1 должен быть 'password1'");

        Optional<User> user2 = userRepository.findByLogin("user2");
        log.info("user2 = " + user2);
        assertTrue(user2.isPresent(), "Пользователь с логином user2 должен быть в базе данных");
        assertEquals("password2", user2.get().getPassword(), "Пароль пользователя user2 должен быть 'password2'");
        log.info("end testDatabaseCreationAndInitialData");
    }

    @Test
    void testAddNewUser() {
        // Добавим нового пользователя и проверим его сохранение
        log.info("start testAddNewUser");
        User newUser = new User();
        newUser.setLogin("newUser");
        newUser.setPassword("newPassword");

        userRepository.save(newUser);
        log.info("saved newUser = " + newUser);

        Optional<User> foundUser = userRepository.findByLogin("newUser");
        log.info("foundUser = " + foundUser);
        assertTrue(foundUser.isPresent(), "Новый пользователь должен быть в базе данных");
        assertEquals("newPassword", foundUser.get().getPassword(), "Пароль нового пользователя должен быть 'newPassword'");
        log.info("end testAddNewUser");
    }
}
