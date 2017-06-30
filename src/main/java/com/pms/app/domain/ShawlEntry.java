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
public class ShawlEntry extends AbstractEntity {



    @ManyToOne
    @JoinColumn(nullable = false)
    private ShawlEntryBatch shawlEntryBatch;

    @ManyToOne
    private ShawlExportBatch shawlExportBatch;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Locations locations;
}
