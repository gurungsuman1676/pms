package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by arjun on 6/27/2017.
 */

@Entity
@Getter
@Setter
public class ShawlProperties extends AbstractEntity {

    private String name;

    private String value;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Shawl shawl;

    private boolean active = true;
}
