package ru.elias.server.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.model.Role;

public abstract class BaseControllerTest {

    private static final String ERRORS_FIRST_STATUS = "$.status";
    private static final String ERRORS_FIRST_EXCEPTION = "$.exception";
    private static final String ERRORS_FIRST_MSG = "$.message";

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    public void setUp(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    /**
     * Выполнение запроса с ответом 200 OK.
     *
     * @param method
     *          HTTP-метод выполняемого запроса
     * @param requestUrl
     *          URL, по которому выполняется запрос
     * @param requestEntity
     *          Тело запроса
     * @throws Exception
     *          Если выполнить запрос не удается
     */
    protected void performOkRequest(HttpMethod method, String requestUrl, Object requestEntity) throws Exception {
        mockMvc.perform(identifyMockRequestBuilder(method, requestUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(requestEntity)))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Выполнение запроса с ответом 200 OK.
     *
     * @param requestUrl
     *          URL, по которому выполняется запрос
     * @throws Exception
     *          Если выполнить запрос не удается
     */
    protected void performOkRequest(String requestUrl) throws Exception {
        mockMvc.perform(identifyMockRequestBuilder(HttpMethod.GET, requestUrl))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Выполнение запроса с ответом 201 Created.
     *
     * @param requestUrl
     *          URL, по которому выполняется запрос
     * @param requestEntity
     *          Тело запроса
     * @throws Exception
     *          Если выполнить запрос не удается
     */
    protected void performCreatedRequest(String requestUrl, String param, Object requestEntity) throws Exception {
        mockMvc.perform(identifyMockRequestBuilder(HttpMethod.POST, requestUrl + param)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(requestEntity)))
               .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * Выполнение запроса с ответом 404 Not Found.
     *
     * @param method
     *          HTTP-метод выполняемого запроса
     * @param requestUrl
     *          URL, по которому выполняется запрос
     * @param requestEntity
     *          Тело запроса
     * @throws Exception
     *          Если выполнить запрос не удается
     */
    protected void performNotFoundRequest(HttpMethod method,
                                          String requestUrl,
                                          Object requestEntity,
                                          ErrorType errorType) throws Exception {
        mockMvc.perform(identifyMockRequestBuilder(method, requestUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(requestEntity)))
               .andExpect(MockMvcResultMatchers.status().isNotFound())
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.jsonPath(
                       ERRORS_FIRST_STATUS,
                       CoreMatchers.is(HttpStatus.NOT_FOUND.value())
               ))
               .andExpect(MockMvcResultMatchers.jsonPath(
                       ERRORS_FIRST_EXCEPTION,
                       CoreMatchers.is(BusinessException.class.getCanonicalName())
               ))
               .andExpect(MockMvcResultMatchers.jsonPath(
                       ERRORS_FIRST_MSG,
                       CoreMatchers.is(errorType.getMessage())
               ));
    }

    /**
     * Определяет {@link MockHttpServletRequestBuilder} для дальнейшего тестирования.
     *
     * @param httpMethod
     *          HTTP-метод, переданный в тесте
     * @param requestUrl
     *          Request URL, переданный в тесте
     *
     * @return {@link MockHttpServletRequestBuilder}
     */
    private MockHttpServletRequestBuilder identifyMockRequestBuilder(HttpMethod httpMethod, String requestUrl) {
        switch (httpMethod) {
            case GET:
                return MockMvcRequestBuilders.get(requestUrl)
                                             .with(user("test@gmail.com").authorities(Role.ADMIN));
            case POST:
                return MockMvcRequestBuilders.post(requestUrl)
                                             .with(user("test@gmail.com").authorities(Role.ADMIN))
                                             .with(csrf());
            case DELETE:
                return MockMvcRequestBuilders.delete(requestUrl)
                                             .with(user("test@gmail.com").authorities(Role.ADMIN));
            case PUT:
                return MockMvcRequestBuilders.put(requestUrl)
                                             .with(user("test@gmail.com").authorities(Role.ADMIN));
            case PATCH:
                return MockMvcRequestBuilders.patch(requestUrl)
                                             .with(user("test@gmail.com").authorities(Role.ADMIN));
            default:
                throw new RuntimeException("Unspecified HTTP method");
        }
    }
}
