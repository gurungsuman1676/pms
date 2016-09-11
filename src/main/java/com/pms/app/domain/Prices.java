package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Prices extends AbstractEntity {
    @ManyToOne
    private Designs design;
    @ManyToOne
    private Sizes size;
    @ManyToOne
    private Yarns yarn;
    private Double amount;
}
