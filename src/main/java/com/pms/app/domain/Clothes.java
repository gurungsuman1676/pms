package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
public class Clothes extends AbstractEntity {
    @ManyToOne
    private Locations location;
    @ManyToOne
    private Prices price;
    private Integer order_no;
    private Date deliver_date;
    @ManyToOne
    private Prints print;
    @ManyToOne
    private Customers customer;
    @Column(name = "shipping_number")
    private String shipping;

    @Column(name = "box_number")
    private String boxNumber;

    @ManyToOne
    private Colors color;

    private String status;

    private Boolean isReturn = false;

     private String weight;
}
