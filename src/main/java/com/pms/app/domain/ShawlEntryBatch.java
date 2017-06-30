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
public class ShawlEntryBatch extends AbstractEntity {
    private int quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ShawlSize shawlSize;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Shawl shawl;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ShawlYarn shawlYarn;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ShawlColor shawlColor;

    @ManyToOne
    @JoinColumn(nullable = true)
    private ShawlCustomer shawlCustomer;


    private String weight;
}
