package ru.elias.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractSpringTest {

    @Autowired
    protected TestDataFactory dataFactory;

    @Autowired
    protected ObjectMapper objectMapper;

}
