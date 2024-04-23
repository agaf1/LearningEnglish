package pl.repository;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.service.domain.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface MapperUserEntity {

    User mapToUser(UserEntity userEntity);

    UserEntity mapToUserEntity(User user);
}
