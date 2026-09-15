package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.exception.UserNotFoundException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;


    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(userModel));
        return userEntityMapper.toModel(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
       return userRepository.existsByEmail(email);
    }

    @Override
    public UserModel getUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toModel)
                .orElseThrow(UserNotFoundException::new);
    }
}