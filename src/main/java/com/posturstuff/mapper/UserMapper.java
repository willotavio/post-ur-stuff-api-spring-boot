package com.posturstuff.mapper;

import com.posturstuff.dto.users.UserViewDto;
import com.posturstuff.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserViewDto userToUserViewDto(Users user);

}
