package fr.rob.technicaltest.service;

import fr.rob.technicaltest.dto.UserRequest;
import fr.rob.technicaltest.dto.UserResponse;

import java.util.Optional;

public interface UserService {
    Optional<UserResponse> getById(Long id);
    UserResponse insertOne(UserRequest user);
}
