package com.maksimov.сlickсounter.service;

import com.maksimov.сlickсounter.db.entity.ClickCounterEntity;
import com.maksimov.сlickсounter.db.repository.ClickCounterRepository;
import com.maksimov.сlickсounter.dto.ClickCounter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClickCounterService implements ReadUpdate{
    private final ClickCounterRepository clickCounterRepository;

    public ClickCounterService(ClickCounterRepository clickCounterRepository) {
        this.clickCounterRepository = clickCounterRepository;
    }


    @Override
    public ClickCounter read() {
        ClickCounter clickCounter = new ClickCounter();
        Long counter;
         if (clickCounterRepository.findById(1L).isPresent()) {
             counter = clickCounterRepository.findById(1L).get().getCounter();
        } else {
             counter = 0L;
         }
             clickCounter.setCounter(counter);
         return clickCounter;
    }

    @Override
    @Transactional
    public boolean update(){
        if (clickCounterRepository.findById(1L).isPresent()) {
            ClickCounterEntity clickCounterEntity = clickCounterRepository.findById(1L).get();
            Long counter = clickCounterEntity.getCounter();
            if (counter != null) {
                if (counter >= 0 && counter < Long.MAX_VALUE) {
                    clickCounterEntity.setCounter(++counter);
                }
            } else
            {
                clickCounterEntity.setCounter(0L);
            }
                clickCounterRepository.save(clickCounterEntity);
                return true;
        } else {
            ClickCounterEntity clickCounterEntity = new ClickCounterEntity();
            clickCounterEntity.setCounter(1L);
            clickCounterEntity.setId(1L);
            clickCounterRepository.save(clickCounterEntity);
        }

        return false;
    }
}
