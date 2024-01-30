package com.fintech.user.mappers;

import com.fintech.user.dto.request.UserRequest;
import com.fintech.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMappers {

    UserMappers INSTANCE = Mappers.getMapper(UserMappers.class);

    UserEntity requestConvertEntity(UserRequest request);

}
