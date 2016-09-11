package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
public class Designs extends AbstractEntity{

    @ManyToOne
    private Customers customer;
    @ManyToOne
    private Designs parent;
    private String name;
    
}
