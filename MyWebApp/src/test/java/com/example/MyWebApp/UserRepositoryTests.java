package com.example.MyWebApp;


import com.example.MyWebApp.user.User;
import com.example.MyWebApp.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("patta123@gmail.com");
        user.setPassword("admin");
        user.setFirstName("Patta");
        user.setLastName("Cell");

        User savedUser = repo.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        assertThat(users).hasSizeGreaterThan(0);

        for(User user: users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("admin1");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        assertThat(updatedUser.getPassword()).isEqualTo("admin1");
    }

    @Test
    public void testGet() {
        Integer userId = 2;
        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 2;
        repo.deleteById(userId);
        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isNotPresent();

    }
}
