package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Prices extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Designs design;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sizes size;

    @ManyToOne
    private Yarns yarn;

    private Double amount;
}
