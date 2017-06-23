package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Colors extends AbstractEntity {

    @Column(nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Yarns yarn;

    @Column(nullable = false)
    private String name_company;


}
