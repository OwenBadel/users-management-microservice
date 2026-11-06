package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class UserResponsePrinterTest {

  @Test
  void shouldPrintSingleUser() {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(""), new PrintStream(out, true, StandardCharsets.UTF_8));
    final UserResponsePrinter printer = new UserResponsePrinter(console);

    final UserResponse user = new UserResponse("1", "Alice", "alice@example.com", "ADMIN", "ACTIVE");
    printer.print(user);

    final String result = out.toString(StandardCharsets.UTF_8);
    assertTrue(result.contains("Alice"));
    assertTrue(result.contains("alice@example.com"));
    assertTrue(result.contains("ADMIN"));
    assertTrue(result.contains("ACTIVE"));
  }

  @Test
  void shouldPrintEmptyList() {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(""), new PrintStream(out, true, StandardCharsets.UTF_8));
    final UserResponsePrinter printer = new UserResponsePrinter(console);

    printer.printList(Collections.emptyList());

    final String result = out.toString(StandardCharsets.UTF_8);
    assertTrue(result.contains("No users found."));
  }

  @Test
  void shouldPrintUserList() {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(""), new PrintStream(out, true, StandardCharsets.UTF_8));
    final UserResponsePrinter printer = new UserResponsePrinter(console);

    final UserResponse user = new UserResponse("1", "Alice", "alice@example.com", "ADMIN", "ACTIVE");
    printer.printList(List.of(user));

    final String result = out.toString(StandardCharsets.UTF_8);
    assertTrue(result.contains("Total: 1 user(s)"));
    assertTrue(result.contains("Alice"));
  }
}
