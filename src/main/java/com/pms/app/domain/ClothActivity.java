package com.pms.app.domain;

import com.mysema.query.annotations.QueryInit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by arrowhead on 7/16/16.
 */

@Getter
@Setter
@Entity
@Table(name = "cloth_activities")
public class ClothActivity extends AbstractEntity{

    @ManyToOne
    private Clothes cloth;

    @ManyToOne
    private Locations location;

    @ManyToOne
    private Users user;
}
