package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by arrowhead on 9/20/15.
 */

@Entity
@Getter
@Setter
public class Sizes extends AbstractEntity {
    private String name;
}
