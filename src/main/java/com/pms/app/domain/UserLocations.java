package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by arrowhead on 9/20/15.
 */

@Getter
@Setter
@Entity
public class UserLocations extends AbstractEntity {
    @ManyToOne
    private Users user;
    @ManyToOne
    private Locations location;
}
