package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class MenuOptionTest {

  @Test
  void shouldFindOptionByNumber() {
    assertEquals(Optional.of(MenuOption.LIST_USERS), MenuOption.fromNumber(1));
    assertEquals(Optional.of(MenuOption.FIND_USER), MenuOption.fromNumber(2));
    assertEquals(Optional.of(MenuOption.CREATE_USER), MenuOption.fromNumber(3));
    assertEquals(Optional.of(MenuOption.UPDATE_USER), MenuOption.fromNumber(4));
    assertEquals(Optional.of(MenuOption.DELETE_USER), MenuOption.fromNumber(5));
    assertEquals(Optional.of(MenuOption.LOGIN), MenuOption.fromNumber(6));
    assertEquals(Optional.of(MenuOption.EXIT), MenuOption.fromNumber(0));
    assertTrue(MenuOption.fromNumber(99).isEmpty());
  }

  @Test
  void shouldHaveCorrectDescriptions() {
    assertEquals("List all users", MenuOption.LIST_USERS.getDescription());
    assertEquals(1, MenuOption.LIST_USERS.getNumber());
  }
}
