package com.pms.app.domain;

import com.mysema.query.annotations.QueryInit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by arjun on 6/20/2017.
 */
@Getter
@Setter
@Entity
public class KnitterMachineHistory extends AbstractEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Knitter knitter;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Machine machine;
    @ManyToOne
    @JoinColumn(nullable = false)
    @QueryInit({"price.design", "price.size", "price.yarn", "customer","color"})
    private Clothes cloth;

    private boolean deleted;
}
