package br.com.erudio.restprojecterudio.integrationtests.testcontainers;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

import org.testcontainers.containers.PostgreSQLContainer;

import static br.com.erudio.restprojecterudio.integrationtests.testcontainers.AbstractIntegrationTest.Initializer.postgres;


@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
                "postgres:16.3-alpine"
        );

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgres)).join();
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testcontainers = new MapPropertySource(
                    "testcontainers",
                    (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testcontainers);
        }
    }

    private static Map<String, String> createConnectionConfiguration() {
        return Map.of(
                "spring.datasource.url:", postgres.getJdbcUrl(),
                "spring.datasource.username:", postgres.getUsername(),
                "spring.datasource.password:", postgres.getPassword()
        );
    }
}
