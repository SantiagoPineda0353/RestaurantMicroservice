package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;

    @Override
    public boolean exitsById(Long id) {
        return categoryRepository.existsById(id);
    }
}