package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
public class Designs extends AbstractEntity{

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customers customer;

    @ManyToOne
    private Designs parent;

    @Column(nullable = false)
    private String name;

    private Double gauge;

    private String setting;
    
}
