package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by arjun on 6/20/2017.
 */
@Getter
@Setter
@Entity
@Table(name = "machine")
public class Machine extends AbstractEntity {

    @Column(nullable = false)
    private String name;
}
