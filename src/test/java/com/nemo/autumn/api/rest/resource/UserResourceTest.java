package com.nemo.autumn.api.rest.resource;

import com.nemo.autumn.api.common.model.UserDto;
import com.nemo.autumn.api.common.model.ValidationResult;
import com.nemo.autumn.domain.Role;
import com.nemo.autumn.domain.User;
import com.nemo.autumn.exception.UserAlreadyExistsException;
import com.nemo.autumn.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.datasource.url = jdbc:h2:mem:test",
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect" })
public class UserResourceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserService userService;

    @Test
    public void givenGetAllUsers_whenCorrectRequest_thenResponseSuccess() {
        User existingUser = createUser();
        when(userService.findAllUsers()).thenReturn(
                Collections.singletonList(existingUser));
        this.webClient.get()
                      .uri("/api/rest/user/all")
                      .exchange()
                      .expectStatus()
                      .isOk();
    }

    @Test
    public void givenGetUser_whenUserExists_thenResponseSuccess() {
        User existingUser = createUser();
        when(userService.retrieveUserById("999")).thenReturn(existingUser);
        this.webClient.get()
                      .uri("/api/rest/user/999")
                      .exchange()
                      .expectStatus()
                      .isOk();

    }

    @Test
    public void givenGetUser_whenUserDoesNotExist_thenResponseNotFound() {
        when(userService.retrieveUserById("-1")).thenReturn(null);
        this.webClient.get()
                      .uri("/api/rest/user/-1")
                      .exchange()
                      .expectStatus()
                      .isNotFound();
    }

    @Test
    public void givenGetUser_whenUserExists_thenCorrectDataRetrieved() {
        User existingUser = createUser();
        when(userService.retrieveUserById("999")).thenReturn(existingUser);
        EntityExchangeResult<UserDto> retrievedUser = this.webClient.get()
                                                                    .uri("/api/rest/user/999")
                                                                    .exchange()
                                                                    .expectBody(
                                                                            UserDto.class)
                                                                    .returnResult();
        assertEquals(existingUser.getId(),
                String.valueOf(retrievedUser.getResponseBody().getId()));
        assertEquals(existingUser.getLogin(),
                retrievedUser.getResponseBody().getLogin());
        assertEquals(existingUser.getRole().getName(),
                retrievedUser.getResponseBody().getRole().getName());
    }

    @Test
    public void givenDeleteUser_whenUserExists_thenResponseSuccess() {
        User existingUser = createUser();
        when(userService.retrieveUserById("999")).thenReturn(existingUser);
        doNothing().when(userService).deleteUser(anyString());
        doNothing().when(userService).deleteUser(any(User.class));
        this.webClient.delete()
                      .uri("/api/rest/user/999")
                      .exchange()
                      .expectStatus()
                      .isOk();
    }

    @Test
    public void givenDeleteUser_whenUserDoesNotExists_thenResponseNotFound() {
        when(userService.retrieveUserById("-1")).thenReturn(null);
        this.webClient.delete()
                      .uri("/api/rest/user/-1")
                      .exchange()
                      .expectStatus()
                      .isNotFound();
    }

    @Test
    public void givenAddUser_whenUserDoesNotExists_whenUserValid_thenResponseCreated()
            throws UserAlreadyExistsException {
        User validUser = createUser();
        UserDto userDto = convertToUserDto(validUser);
        doNothing().when(userService).createUser(any(User.class));
        this.webClient.post()
                      .uri("/api/rest/user")
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isCreated();
    }

    @Test
    public void givenAddUser_whenUserExists_thenResponseConflict()
            throws UserAlreadyExistsException {
        User validUser = createUser();
        UserDto userDto = convertToUserDto(validUser);
        doThrow(UserAlreadyExistsException.class).when(userService)
                                                 .createUser(any(User.class));
        this.webClient.post()
                      .uri("/api/rest/user")
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isEqualTo(Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    public void givenAddUser_whenUserInvalid_thenResponseBadRequest()
            throws UserAlreadyExistsException {
        User user = createUser();
        user.setPassword("abc"); // invalid password
        UserDto userDto = convertToUserDto(user);
        doNothing().when(userService).createUser(any(User.class));
        this.webClient.post()
                      .uri("/api/rest/user")
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isBadRequest();
    }

    @Test
    public void givenAddUser_whenUserInvalid_thenValidationResultRetrieved()
            throws UserAlreadyExistsException {
        User user = createUser();
        user.setPassword("abc"); // invalid password
        UserDto userDto = convertToUserDto(user);
        doNothing().when(userService).createUser(any(User.class));
        EntityExchangeResult<ValidationResult> validationResult = this.webClient
                .post()
                .uri("/api/rest/user")
                .body(BodyInserters.fromObject(userDto))
                .exchange()
                .expectBody(ValidationResult.class)
                .returnResult();
        assertEquals("Validation error",
                validationResult.getResponseBody().getMessage());
        assertEquals(1, validationResult.getResponseBody().getItems().size());
        assertEquals("password", validationResult.getResponseBody()
                                                 .getItems()
                                                 .get(0)
                                                 .getField());
        assertEquals(3, validationResult.getResponseBody()
                                        .getItems()
                                        .get(0)
                                        .getMessages()
                                        .size());
    }

    @Test
    public void givenUpdateUser_whenUserValid_thenResponseAccepted() {
        User validUser = createUser();
        UserDto userDto = convertToUserDto(validUser);
        userDto.setId(validUser.getId());
        doReturn(validUser).when(userService)
                           .retrieveUserById(validUser.getId());
        doNothing().when(userService).updateUser(any(User.class));
        this.webClient.put()
                      .uri("/api/rest/user/" + validUser.getId())
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isAccepted();
    }

    @Test
    public void givenUpdateUser_whenUserDoesNotExist_thenResponseNotFound() {
        User validUser = createUser();
        UserDto userDto = convertToUserDto(validUser);
        userDto.setId(validUser.getId());
        doReturn(null).when(userService).retrieveUserById(validUser.getId());
        doNothing().when(userService).updateUser(any(User.class));
        this.webClient.put()
                      .uri("/api/rest/user/" + validUser.getId())
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isNotFound();
    }

    @Test
    public void givenUpdateUser_whenIdsDoNotMatch_thenResponseConflict() {
        User validUser = createUser();
        UserDto userDto = convertToUserDto(validUser);
        userDto.setId("-1");
        doNothing().when(userService).updateUser(any(User.class));
        this.webClient.put()
                      .uri("/api/rest/user/" + validUser.getId())
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isEqualTo(Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    public void givenUpdateUser_whenUserInvalid_thenResponseBadRequest() {
        User invalidUser = createUser();
        invalidUser.setPassword("abc"); // invalid password
        UserDto userDto = convertToUserDto(invalidUser);
        userDto.setId(invalidUser.getId());
        doReturn(invalidUser).when(userService)
                             .retrieveUserById(invalidUser.getId());
        doNothing().when(userService).updateUser(any(User.class));
        this.webClient.put()
                      .uri("/api/rest/user/" + invalidUser.getId())
                      .body(BodyInserters.fromObject(userDto))
                      .exchange()
                      .expectStatus()
                      .isBadRequest();
    }

    @Test
    public void givenUpdateUser_whenUserInvalid_thenValidationResultRetrieved() {
        User invalidUser = createUser();
        invalidUser.setPassword("abc"); // invalid password
        UserDto userDto = convertToUserDto(invalidUser);
        userDto.setId(invalidUser.getId());
        doReturn(invalidUser).when(userService)
                             .retrieveUserById(invalidUser.getId());
        doNothing().when(userService).updateUser(any(User.class));
        EntityExchangeResult<ValidationResult> validationResult = this.webClient
                .put()
                .uri("/api/rest/user/" + invalidUser.getId())
                .body(BodyInserters.fromObject(userDto))
                .exchange()
                .expectBody(ValidationResult.class)
                .returnResult();
        assertEquals("Validation error",
                validationResult.getResponseBody().getMessage());
        assertEquals(1, validationResult.getResponseBody().getItems().size());
        assertEquals("password", validationResult.getResponseBody()
                                                 .getItems()
                                                 .get(0)
                                                 .getField());
        assertEquals(3, validationResult.getResponseBody()
                                        .getItems()
                                        .get(0)
                                        .getMessages()
                                        .size());
    }

    private User createUser() {
        return new User("999", "mock", "password!!11", "mock@email", "Mock",
                "Mock", new Date(100000000000L), new Role("123213", "admin"));
    }

    private UserDto convertToUserDto(User user) {
        return new UserDto(null, user.getLogin(), user.getEmail(),
                user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getBirthday(), user.getRole());
    }

}