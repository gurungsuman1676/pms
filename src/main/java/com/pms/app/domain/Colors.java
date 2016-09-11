package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Colors extends AbstractEntity{
    private String code;
    @ManyToOne
    private Yarns yarn;
    private String name;
}