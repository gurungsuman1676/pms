package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by arjun on 7/5/2017.
 */
@Entity
@Getter
@Setter
public class WeavingWorkLog extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Locations location;

    private Integer orderNo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customers customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sizes size;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Designs design;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Prints print;

    private int quantity;

    @ManyToOne
    private Colors color;

    private String extraField;

    private String receiptNumber;


}
