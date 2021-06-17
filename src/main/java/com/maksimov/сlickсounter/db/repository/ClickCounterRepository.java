package com.maksimov.сlickсounter.db.repository;

import com.maksimov.сlickсounter.db.entity.ClickCounterEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClickCounterRepository extends CrudRepository<ClickCounterEntity,Long> {
}
