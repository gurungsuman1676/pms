package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by arjun on 6/27/2017.
 */

@Entity
@Getter
@Setter
public class ShawlInventory extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Designs designs;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sizes sizes;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ShawlColor color;

    @ManyToOne
    @JoinColumn Customers customer;

    private int count;
}
