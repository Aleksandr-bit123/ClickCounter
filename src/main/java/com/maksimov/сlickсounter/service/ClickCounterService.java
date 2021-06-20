package com.maksimov.сlickсounter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimov.сlickсounter.db.entity.ClickCounterEntity;
import com.maksimov.сlickсounter.db.repository.ClickCounterRepository;
import com.maksimov.сlickсounter.dto.ClickCounter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class ClickCounterService implements ReadUpdate {
    private final ClickCounterRepository clickCounterRepository;
    private final ObjectMapper objectMapper;

    public ClickCounterService(ClickCounterRepository clickCounterRepository, ObjectMapper objectMapper) {
        this.clickCounterRepository = clickCounterRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @PostConstruct
    public void init() {
        if (clickCounterRepository.findById(1L).isEmpty()) {
            ClickCounterEntity clickCounterEntity = objectMapper.convertValue(new ClickCounter(), ClickCounterEntity.class);
            clickCounterRepository.save(clickCounterEntity);
        }
    }

    @Override
    public ClickCounter read() {
        ClickCounter clickCounter = null;
        Optional<ClickCounterEntity> clickCounterEntityOptional = clickCounterRepository.findById(1L);
        if (clickCounterEntityOptional.isPresent()) {
            try {
                clickCounter = objectMapper.convertValue(clickCounterEntityOptional, ClickCounter.class);
                if (clickCounter.getCounter() < 0L || clickCounter.getCounter() == null) {
                    return null;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return clickCounter;
    }

    @Override
    @Transactional
    public boolean update() {
        ClickCounter clickCounter;
        Optional<ClickCounterEntity> clickCounterEntityOptional = clickCounterRepository.findById(1L);
        if (clickCounterEntityOptional.isPresent()) {
            try {
                clickCounter = objectMapper.convertValue(clickCounterEntityOptional, ClickCounter.class);
                if (clickCounter.getCounter() >= 0L && clickCounter.getCounter() < Long.MAX_VALUE) {
                    clickCounter.increment();
                    ClickCounterEntity clickCounterEntity = objectMapper.convertValue(clickCounter, ClickCounterEntity.class);
                    clickCounterRepository.save(clickCounterEntity);
                    return true;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
