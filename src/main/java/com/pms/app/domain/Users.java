package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Users extends AbstractEntity {
    private String username;
    private Roles role;
    private String password;
}
