package org.orderapi.user.mappers;

import org.orderapi.user.controllers.dto.UserDto;
import org.orderapi.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    @Override
    public User toDomain(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
