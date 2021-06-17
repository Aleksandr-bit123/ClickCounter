package com.maksimov.сlickсounter.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(schema = "public", name = "t_counter")
public class ClickCounterEntity {
    @Id
    private Long id;
    private Long counter;
}

