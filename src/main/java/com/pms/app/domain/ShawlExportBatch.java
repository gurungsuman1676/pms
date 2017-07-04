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
public class ShawlExportBatch extends AbstractEntity {

    private int quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customers customer;
}
