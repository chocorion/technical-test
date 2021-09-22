package fr.rob.technicaltest.mapper;

import fr.rob.technicaltest.dto.UserRequest;
import fr.rob.technicaltest.dto.UserResponse;
import fr.rob.technicaltest.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    UserResponse toUserDto(User user);
    User toUser(UserRequest user);
}
