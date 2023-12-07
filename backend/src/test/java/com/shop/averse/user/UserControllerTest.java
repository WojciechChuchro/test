package com.shop.averse.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.averse.controllers.UserController;
import com.shop.averse.database.models.Role;
import com.shop.averse.database.models.User;
import com.shop.averse.database.repositories.UserRepository;
import com.shop.averse.services.UserService;
import com.shop.averse.util.StatusMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void getUserByIdTest() throws JsonProcessingException {
        String url = "http://localhost:" + port + "/api/users/1";
        String response = restTemplate.getForObject(url, String.class);

        Optional<User> foundUser = userRepository.findById(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(foundUser.get());

        assertEquals(json, response);
    }

    @Test
    public void getAllUsersTest() throws JsonProcessingException {
        String url = "http://localhost:" + port + "/api/users";
        String response = restTemplate.getForObject(url, String.class);

        List<User> foundUsers = userRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(foundUsers);

        assertEquals(json, response);
    }

    @Test
    public void updateUserTest() {

    }

    @Test
    public void createUserTest() {

    }

    @Test
    public void testDeleteUserSuccess() {
        // Mock the userService to return true when deleteUser is called with id 1
        when(userService.deleteUser(1L)).thenReturn(true);

        // Create a request entity with id 1
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, new HttpHeaders());

        // Send the delete request to the controller
        ResponseEntity<StatusMessageResponse> response = restTemplate.exchange("/1", HttpMethod.DELETE, requestEntity, StatusMessageResponse.class);

        // Verify the response status code
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // Verify the response message
        assertEquals("User deletion completed.", response.getBody().getMessage());

        // Verify that the response status is true
        assertTrue(response.getBody().getStatus());
    }
}
