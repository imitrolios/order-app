package org.orderapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.orderapi.common.web.model.ErrorResponseDto;
import org.orderapi.user.controllers.dto.UserDto;
import org.orderapi.user.model.User;
import org.orderapi.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserApplicationTests {

    private static final String BASE_URL = "http://localhost:8081/user/";
    private static final String TEST_EMAIL = "test@test.test";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSubmitUser() throws Exception {

        UserDto userDto = createUserRequestForTest();

        ObjectMapper om = new ObjectMapper();
        String userRequestAsString = om.writeValueAsString(userDto);
        MvcResult userResult = this.mockMvc.perform(post(BASE_URL).
                content(userRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        userDto = om.readValue(userResult.getResponse().getContentAsString(), UserDto.class);

        assertEquals(userResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE,
                "Content type not JSON");
        assertEquals(userResult.getResponse().getStatus(), HttpStatus.CREATED.value(),
                "HttpStatus not 201 Created");
        assertNotNull(userDto.getId(), "userDto.id is null");
        assertNotNull(userDto.getEmail(), "userDto.email is not equal to user.email");
    }

    @Test
    void testFetchUserById() throws Exception {
        User user = createUserForTest();
        userRepository.saveAndFlush(user);

        MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + user.getId())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        UserDto userDto = om.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(userDto);
        assertNotNull(userDto.getId(), "userDto.id is null");
        assertEquals(userDto.getEmail(), user.getEmail(), "userDto.email is not equal to user.email");
    }

    @Test
    void testFetchUserByUsername() throws Exception {
        User user = createUserForTest();
        userRepository.saveAndFlush(user);

        MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "search/email/" + TEST_EMAIL)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        UserDto userDto = om.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(userDto);
        assertNotNull(userDto.getId(), "userDto.id is null");
        assertEquals(userDto.getEmail(), user.getEmail(), "userDto.email is not equal to user.email");
    }

    @Test
    void testFetchUserByIdThatDoesNotExistReturns400() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + UUID.randomUUID())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        ErrorResponseDto errorResponseDto = om.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        assertEquals(errorResponseDto.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(errorResponseDto.getError());
        assertNotNull(errorResponseDto.getMessage());
    }

    private User createUserForTest() {
        User user = new User();
        user.setEmail(TEST_EMAIL);
        return user;
    }

    private UserDto createUserRequestForTest() {

        UserDto userDto = new UserDto();
        userDto.setEmail(TEST_EMAIL);
        return userDto;
    }

    @AfterEach
    private void truncateTablesInDb() {
        userRepository.deleteAll();
    }

}
