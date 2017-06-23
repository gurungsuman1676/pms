package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Locations extends AbstractEntity {

    @Column(nullable = false)
    private String name;
}
