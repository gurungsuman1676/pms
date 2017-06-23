package com.pms.app.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class Currency extends AbstractEntity {

    @Column(nullable = false)
    private String name;
}
