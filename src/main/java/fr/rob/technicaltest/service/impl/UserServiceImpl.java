package fr.rob.technicaltest.service.impl;

import fr.rob.technicaltest.dto.UserRequest;
import fr.rob.technicaltest.dto.UserResponse;
import fr.rob.technicaltest.entity.User;
import fr.rob.technicaltest.mapper.MapStructMapper;
import fr.rob.technicaltest.repository.UserRepository;
import fr.rob.technicaltest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final MapStructMapper mapper;

    public UserServiceImpl(UserRepository repository, MapStructMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserResponse> getById(Long id) {
        return repository.findById(id).map(mapper::toUserDto);
    }

    @Override
    public UserResponse insertOne(UserRequest user) {
        User savedUser = repository.save(mapper.toUser(user));
        return mapper.toUserDto(savedUser);
    }
}
