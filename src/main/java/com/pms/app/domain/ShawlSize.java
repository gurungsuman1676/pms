package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by arjun on 6/27/2017.
 */

@Entity
@Setter
@Getter
public class ShawlSize  extends AbstractEntity{
    private String name;
}
