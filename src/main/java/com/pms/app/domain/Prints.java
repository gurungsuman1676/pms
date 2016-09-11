package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
public class Prints extends AbstractEntity {
    private String name;
    @ManyToOne
    private Sizes size;
    private Double amount;

    @ManyToOne
    private Currency currency;
}
