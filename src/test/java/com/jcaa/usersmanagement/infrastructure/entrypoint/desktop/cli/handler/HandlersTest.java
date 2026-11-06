package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.LoginRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HandlersTest {

  private UserController userController;
  private ConsoleIO console;
  private UserResponsePrinter printer;

  @BeforeEach
  void setUp() {
    userController = mock(UserController.class);
    console = mock(ConsoleIO.class);
    printer = mock(UserResponsePrinter.class);
  }

  @Test
  void shouldHandleCreateUserSuccess() {
    when(console.readRequired(any())).thenReturn("1", "John", "john@example.com", "pass1234", "ADMIN");
    final UserResponse user = new UserResponse("1", "John", "john@example.com", "ADMIN", "ACTIVE");
    when(userController.createUser(any(CreateUserRequest.class))).thenReturn(user);

    final CreateUserHandler handler = new CreateUserHandler(userController, console, printer);
    handler.handle();

    verify(printer).print(user);
  }

  @Test
  void shouldHandleCreateUserAlreadyExists() {
    when(console.readRequired(any())).thenReturn("1", "John", "john@example.com", "pass1234", "ADMIN");
    when(userController.createUser(any(CreateUserRequest.class)))
        .thenThrow(UserAlreadyExistsException.becauseEmailAlreadyExists("john@example.com"));

    final CreateUserHandler handler = new CreateUserHandler(userController, console, printer);
    handler.handle();

    verify(console).println(any());
  }

  @Test
  void shouldHandleDeleteUserSuccess() {
    when(console.readRequired(any())).thenReturn("1");

    final DeleteUserHandler handler = new DeleteUserHandler(userController, console);
    handler.handle();

    verify(userController).deleteUser("1");
  }

  @Test
  void shouldHandleDeleteUserNotFound() {
    when(console.readRequired(any())).thenReturn("1");
    org.mockito.Mockito.doThrow(UserNotFoundException.becauseIdWasNotFound("1"))
        .when(userController).deleteUser("1");

    final DeleteUserHandler handler = new DeleteUserHandler(userController, console);
    handler.handle();

    verify(console).println(any());
  }

  @Test
  void shouldHandleFindUserByIdSuccess() {
    when(console.readRequired(any())).thenReturn("1");
    final UserResponse user = new UserResponse("1", "John", "john@example.com", "ADMIN", "ACTIVE");
    when(userController.findUserById("1")).thenReturn(user);

    final FindUserByIdHandler handler = new FindUserByIdHandler(userController, console, printer);
    handler.handle();

    verify(printer).print(user);
  }

  @Test
  void shouldHandleFindUserByIdNotFound() {
    when(console.readRequired(any())).thenReturn("1");
    when(userController.findUserById("1")).thenThrow(UserNotFoundException.becauseIdWasNotFound("1"));

    final FindUserByIdHandler handler = new FindUserByIdHandler(userController, console, printer);
    handler.handle();

    verify(console).println(any());
  }

  @Test
  void shouldHandleListUsers() {
    final List<UserResponse> list = List.of(new UserResponse("1", "John", "john@example.com", "ADMIN", "ACTIVE"));
    when(userController.listAllUsers()).thenReturn(list);

    final ListUsersHandler handler = new ListUsersHandler(userController, printer);
    handler.handle();

    verify(printer).printList(list);
  }

  @Test
  void shouldHandleLoginSuccess() {
    when(console.readRequired(any())).thenReturn("john@example.com", "pass1234");
    final UserResponse user = new UserResponse("1", "John", "john@example.com", "ADMIN", "ACTIVE");
    when(userController.login(any(LoginRequest.class))).thenReturn(user);

    final LoginHandler handler = new LoginHandler(userController, console, printer);
    handler.handle();

    verify(printer).print(user);
  }

  @Test
  void shouldHandleLoginFailed() {
    when(console.readRequired(any())).thenReturn("john@example.com", "wrong");
    when(userController.login(any(LoginRequest.class))).thenThrow(InvalidCredentialsException.becauseCredentialsAreInvalid());

    final LoginHandler handler = new LoginHandler(userController, console, printer);
    handler.handle();

    verify(console).println(any());
  }

  @Test
  void shouldHandleUpdateUserSuccess() {
    when(console.readRequired(any())).thenReturn("1", "John New", "johnnew@example.com", "ADMIN", "ACTIVE");
    when(console.readOptional(any())).thenReturn("newpass1234");
    final UserResponse user = new UserResponse("1", "John New", "johnnew@example.com", "ADMIN", "ACTIVE");
    when(userController.updateUser(any(UpdateUserRequest.class))).thenReturn(user);

    final UpdateUserHandler handler = new UpdateUserHandler(userController, console, printer);
    handler.handle();

    verify(printer).print(user);
  }

  @Test
  void shouldHandleUpdateUserNotFound() {
    when(console.readRequired(any())).thenReturn("1", "John New", "johnnew@example.com", "ADMIN", "ACTIVE");
    when(console.readOptional(any())).thenReturn("");
    when(userController.updateUser(any(UpdateUserRequest.class)))
        .thenThrow(UserNotFoundException.becauseIdWasNotFound("1"));

    final UpdateUserHandler handler = new UpdateUserHandler(userController, console, printer);
    handler.handle();

    verify(console).println(any());
  }
}
