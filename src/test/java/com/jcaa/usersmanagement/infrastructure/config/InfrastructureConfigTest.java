package com.jcaa.usersmanagement.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jcaa.usersmanagement.infrastructure.adapter.email.BrevoConfig;
import java.time.Duration;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class InfrastructureConfigTest {

  @Test
  void shouldCreateAsyncNotificationExecutor() {
    final AsyncNotificationConfig config = new AsyncNotificationConfig();
    final Executor executor = config.notificationExecutor();
    assertNotNull(executor);
  }

  @Test
  void shouldCreateBrevoConfigAndRestClient() {
    final BrevoSpringConfig config = new BrevoSpringConfig();
    final BrevoConfig brevoConfig = config.brevoConfig(
        "https://api.brevo.com",
        "xkeysib-1234567890abcdef1234567890abcdef",
        "from@example.com",
        "Sender",
        Duration.ofSeconds(2),
        Duration.ofSeconds(5),
        3,
        Duration.ofMillis(500),
        2.0,
        50,
        10,
        5,
        Duration.ofSeconds(30)
    );

    assertNotNull(brevoConfig);
    assertEquals("https://api.brevo.com", brevoConfig.baseUrl());
    assertEquals("from@example.com", brevoConfig.fromAddress());

    final RestClient client = config.brevoRestClient(brevoConfig);
    assertNotNull(client);
  }
}
