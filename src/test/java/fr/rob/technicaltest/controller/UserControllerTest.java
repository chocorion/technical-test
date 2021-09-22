package fr.rob.technicaltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.rob.technicaltest.dto.UserRequest;
import fr.rob.technicaltest.dto.UserResponse;
import fr.rob.technicaltest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserServiceImpl service;

    UserResponse simpleUser = new UserResponse(1L, "Robin", LocalDate.now(), "FRA", null, null);
    UserResponse completeUser = new UserResponse(1L, "Robin", LocalDate.now(), "FRA", "0666666666", "m");

    @Test
    public void testGetValidId() throws Exception {
        when(service.getById(1L)).thenReturn(Optional.of(simpleUser));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(simpleUser)));
    }

    @Test
    public void testGetWrongId() throws Exception {
        when(service.getById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSendCorrectSimpleUser() throws Exception {
        UserRequest userRequest = new UserRequest("Robin", LocalDate.now().minusYears(20), "FRA", null, null);
        when(service.insertOne(userRequest)).thenReturn(simpleUser);

        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/v1/users/1"))
                .andExpect(content().string(objectMapper.writeValueAsString(simpleUser)));
    }

    @Test
    public void testSendCorrectCompleteUser() throws Exception {
        UserRequest userRequest = new UserRequest("Robin", LocalDate.now().minusYears(20), "FRA", "0666666666", "m");
        when(service.insertOne(userRequest)).thenReturn(completeUser);

        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/v1/users/1"))
                .andExpect(content().string(objectMapper.writeValueAsString(completeUser)));
    }

    @Test
    public void testSendIncorrectUserEmptyName() throws Exception {
        assertBadRequest(new UserRequest("", LocalDate.now().minusYears(20), "FRA", "0666666666", "m"));
    }

    @Test
    public void testSendIncorrectUserToYoung() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(10), "FRA", "0666666666", "m"));
    }

    @Test
    public void testSendIncorrectUserBadCountry() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "GNE", "0666666666", "m"));
    }

    @Test
    public void testSendIncorrectUserEmptyCountry() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "", "0666666666", "m"));
    }

    @Test
    public void testSendIncorrectUserToLongPhoneNumber() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "GNE", "06666666667", "m"));
    }

    @Test
    public void testSendIncorrectUserToShortPhoneNumber() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "GNE", "06666666", "m"));
    }

    @Test
    public void testSendIncorrectUserEmptyPhoneNumber() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "GNE", "", "m"));
    }

    @Test
    public void testSendIncorrectUserBadGender() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "GNE", "0666666666", "e"));
    }

    @Test
    public void testSendIncorrectUserEmptyGender() throws Exception {
        assertBadRequest(new UserRequest("Robin", LocalDate.now().minusYears(20), "GNE", "0666666666", ""));
    }

    private void assertBadRequest(UserRequest user) throws Exception {
        when(service.insertOne(user)).thenReturn(completeUser);

        mockMvc.perform(
                post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}