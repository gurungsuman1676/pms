package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by arjun on 6/28/2017.
 */

@Entity
@Setter
@Getter
public class ShawlActivity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private ShawlEntry shawlEntry;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Locations location;
  }
