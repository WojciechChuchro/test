package com.shop.averse.user;

import com.shop.averse.controllers.UserController;
import com.shop.averse.database.models.Role;
import com.shop.averse.database.models.User;
import com.shop.averse.database.repositories.UserRepository;
import com.shop.averse.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
//@TestPropertySource("/application-test.yml")
public class UserServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setupDatabase() {
//        String encodedPassword = passwordEncoder.encode("password");
//        jdbc.execute("insert into _user (id, name, surname, email, username) " +
//                "values (3, 'Alice', 'Smith', 'alice@email.com', 'alice')");
    }

    @Test
    public void createUser() {
        User newUser = User.builder()
                .name("John")
                .surname("Smith")
                .email("john@email.com")
                .username("john")
                .password("P@$$w0rd")
                .roleModel(Role.USER)
                .build();

        when(userRepository.findByEmail("john@email.com")).thenReturn(newUser);

        assertFalse(userService.createUser(newUser));

        when(userRepository.findByEmail("john@email.com")).thenReturn(null);

        assertTrue(userService.createUser(newUser));
    }

    @Test
    public void testDeleteUser() {
        // Case 1: Valid user ID
        Long validUserId = 1L;
        User userToDelete = new User();
        when(userRepository.findById(validUserId)).thenReturn(Optional.of(userToDelete));

        assertTrue(userService.deleteUser(validUserId));

        // Verify that deleteById was called with the expected ID
        verify(userRepository, times(1)).deleteById(validUserId);

        // Case 2: Invalid user ID
        Long invalidUserId = 2L;
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        assertFalse(userService.deleteUser(invalidUserId));

        // Verify that deleteById was not called for an invalid user ID
        verify(userRepository, never()).deleteById(invalidUserId);
    }

    @Test
    public void updateUser() {

    }

    @Test
    public void getAllUser() {

    }
}
