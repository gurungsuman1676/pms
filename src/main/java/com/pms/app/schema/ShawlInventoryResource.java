package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by arjun on 6/27/2017.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShawlInventoryResource {
    private Long id;
    private DesignResource design;
    private CustomerResource importCustomer;
    private CustomerResource exportCustomer;
    private ShawlColorResource color;
    private SizeResource size;

    @QueryProjection
    public ShawlInventoryResource(Long id,
                                  String shawlName,
                                  String importCustomer,
                                  String exportCustomer,
                                  String color,
                                  String size,
                                  String location,
                                  Date importDate,
                                  Date exportDate) {
        this.id = id;
    }
}
