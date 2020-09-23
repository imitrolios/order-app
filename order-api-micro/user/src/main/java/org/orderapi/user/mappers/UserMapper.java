package org.orderapi.user.mappers;

import org.orderapi.user.controllers.dto.UserDto;
import org.orderapi.user.model.User;

public interface UserMapper {

    UserDto toDto(User user);

    User toDomain(UserDto userDto);

}
