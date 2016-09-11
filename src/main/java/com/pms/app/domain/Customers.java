package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
public class Customers extends AbstractEntity {
    private String name;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Customers parent;
}
