package pl.controller;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.service.domain.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {

    User mapToUser(UserDTO userDTO);

    UserDTO mapToUserDTO(User user);
}
