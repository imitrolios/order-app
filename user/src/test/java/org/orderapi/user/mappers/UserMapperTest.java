package org.orderapi.user.mappers;

import org.junit.jupiter.api.Test;
import org.orderapi.user.controllers.dto.UserDto;
import org.orderapi.user.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    public void when_userDomain_mapper_should_return_userDto() {

        User user = new User();
        user.setId(UUID.randomUUID());

        UserDto userDto = userMapper.toDto(user);

        assertNotNull(userDto, "userDto is null");
    }

    @Test
    public void when_userDto_mapper_should_return_userDomain() {

        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());

        User user = userMapper.toDomain(userDto);

        assertNotNull(user, "userDto is null");
        assertEquals(user.getId(), userDto.getId(), "userDto.id not equal to user.id");
    }

}