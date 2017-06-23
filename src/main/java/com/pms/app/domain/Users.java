package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Users extends AbstractEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Roles role;

    @Column(nullable = false)
    private String password;
}
