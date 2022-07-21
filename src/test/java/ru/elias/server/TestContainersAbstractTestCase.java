package ru.elias.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.elias.server.annotation.IntegrationTest;
import ru.elias.server.config.DatabaseContainer;

@IntegrationTest
@Testcontainers
public class TestContainersAbstractTestCase {

    @Container
    private static final DatabaseContainer DATABASE_CONTAINER = DatabaseContainer.getInstance();

    @Test
    void test() {
        Assertions.assertTrue(DATABASE_CONTAINER.isRunning());
    }

}
