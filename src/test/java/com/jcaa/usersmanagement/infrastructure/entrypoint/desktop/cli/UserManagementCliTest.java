package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserManagementCliTest {

  private UserController userController;
  private ConsoleIO console;

  @BeforeEach
  void setUp() {
    userController = mock(UserController.class);
    console = mock(ConsoleIO.class);
  }

  @Test
  void shouldStartAndExit() {
    when(console.readInt(any())).thenReturn(0);

    final UserManagementCli cli = new UserManagementCli(userController, console);
    cli.start();

    verify(console).println("\n  Goodbye!\n");
  }

  @Test
  void shouldHandleInvalidOptionThenExit() {
    when(console.readInt(any())).thenReturn(99, 0);

    final UserManagementCli cli = new UserManagementCli(userController, console);
    cli.start();

    verify(console).println("  Invalid option. Please try again.");
    verify(console).println("\n  Goodbye!\n");
  }

  @Test
  void shouldExecuteListUsersThenExit() {
    when(console.readInt(any())).thenReturn(1, 0);
    when(userController.listAllUsers()).thenReturn(Collections.emptyList());

    final UserManagementCli cli = new UserManagementCli(userController, console);
    cli.start();

    verify(userController).listAllUsers();
  }

  @Test
  void shouldCatchConstraintViolationException() {
    when(console.readInt(any())).thenReturn(1, 0);
    when(userController.listAllUsers())
        .thenThrow(new ConstraintViolationException("Violations", Collections.emptySet()));

    final UserManagementCli cli = new UserManagementCli(userController, console);
    cli.start();

    verify(console).println("  Validation errors:");
  }

  @Test
  void shouldCatchRuntimeException() {
    when(console.readInt(any())).thenReturn(1, 0);
    when(userController.listAllUsers()).thenThrow(new RuntimeException("DB crash"));

    final UserManagementCli cli = new UserManagementCli(userController, console);
    cli.start();

    verify(console).println("  Unexpected error: DB crash");
  }
}
