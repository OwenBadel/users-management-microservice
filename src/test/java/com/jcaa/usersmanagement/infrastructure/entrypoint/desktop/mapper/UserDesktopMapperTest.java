package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUserCommand;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUserCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUserByIdQuery;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.LoginRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserDesktopMapperTest {

  @Test
  void shouldMapToCreateCommand() {
    final CreateUserRequest req = new CreateUserRequest("1", "John", "john@example.com", "pass1234", "ADMIN");
    final CreateUserCommand cmd = UserDesktopMapper.toCreateCommand(req);

    assertEquals("1", cmd.id());
    assertEquals("John", cmd.name());
    assertEquals("john@example.com", cmd.email());
    assertEquals("pass1234", cmd.password());
    assertEquals("ADMIN", cmd.role());
  }

  @Test
  void shouldMapToUpdateCommand() {
    final UpdateUserRequest req = new UpdateUserRequest("1", "John", "john@example.com", "pass1234", "ADMIN", "ACTIVE");
    final UpdateUserCommand cmd = UserDesktopMapper.toUpdateCommand(req);

    assertEquals("1", cmd.id());
    assertEquals("John", cmd.name());
    assertEquals("john@example.com", cmd.email());
    assertEquals("pass1234", cmd.password());
    assertEquals("ADMIN", cmd.role());
    assertEquals("ACTIVE", cmd.status());
  }

  @Test
  void shouldMapToDeleteCommand() {
    final DeleteUserCommand cmd = UserDesktopMapper.toDeleteCommand("123");
    assertEquals("123", cmd.id());
  }

  @Test
  void shouldMapToGetByIdQuery() {
    final GetUserByIdQuery query = UserDesktopMapper.toGetByIdQuery("123");
    assertEquals("123", query.id());
  }

  @Test
  void shouldMapToLoginCommand() {
    final LoginRequest req = new LoginRequest("john@example.com", "secret");
    final LoginCommand cmd = UserDesktopMapper.toLoginCommand(req);

    assertEquals("john@example.com", cmd.email());
    assertEquals("secret", cmd.password());
  }

  @Test
  void shouldMapToResponseAndList() {
    final UserModel model = new UserModel(
        new UserId("1"),
        new UserName("John"),
        new UserEmail("john@example.com"),
        UserPassword.fromHash("$2a$12$hash"),
        UserRole.ADMIN,
        UserStatus.ACTIVE
    );

    final UserResponse response = UserDesktopMapper.toResponse(model);
    assertEquals("1", response.id());
    assertEquals("John", response.name());
    assertEquals("john@example.com", response.email());
    assertEquals("ADMIN", response.role());
    assertEquals("ACTIVE", response.status());

    final List<UserResponse> list = UserDesktopMapper.toResponseList(List.of(model));
    assertEquals(1, list.size());
    assertEquals("1", list.get(0).id());
  }
}
