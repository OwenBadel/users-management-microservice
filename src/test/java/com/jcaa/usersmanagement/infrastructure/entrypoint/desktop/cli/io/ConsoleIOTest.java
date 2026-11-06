package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ConsoleIOTest {

  @Test
  void shouldReadRequiredInput() {
    final String input = "   \n  validValue  \n";
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(input), new PrintStream(out, true, StandardCharsets.UTF_8));

    final String result = console.readRequired("Enter: ");
    assertEquals("validValue", result);
  }

  @Test
  void shouldReadOptionalInput() {
    final String input = " optionalValue \n";
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(input), new PrintStream(out, true, StandardCharsets.UTF_8));

    final String result = console.readOptional("Enter optional: ");
    assertEquals("optionalValue", result);
  }

  @Test
  void shouldReadInt() {
    final String input = "abc\n 42 \n";
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(input), new PrintStream(out, true, StandardCharsets.UTF_8));

    final int result = console.readInt("Enter number: ");
    assertEquals(42, result);
  }

  @Test
  void shouldPrintMessages() {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ConsoleIO console = new ConsoleIO(new Scanner(""), new PrintStream(out, true, StandardCharsets.UTF_8));

    console.println("Hello");
    console.println();
    console.printf("Format %s %d", "arg", 1);

    final String printed = out.toString(StandardCharsets.UTF_8);
    org.junit.jupiter.api.Assertions.assertTrue(printed.contains("Hello"));
    org.junit.jupiter.api.Assertions.assertTrue(printed.contains("Format arg 1"));
  }
}
