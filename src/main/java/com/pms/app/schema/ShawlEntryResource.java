package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import com.pms.app.domain.ShawlCustomer;
import com.pms.app.domain.ShawlYarn;
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
public class ShawlEntryResource {
    private Long id;
    private ShawlResource shawl;
    private ShawlCustomerResource importCustomer;
    private ShawlCustomerResource exportCustomer;
    private ShawlYarnResource yarn;
    private ShawlColorResource color;
    private ShawlSizeResource size;
    private Date importDate;
    private Date exportDate;
    private LocationResource location;

    @QueryProjection
    public ShawlEntryResource(Long id,
                              String shawlName,
                              String importCustomer,
                              String exportCustomer,
                              String yarn,
                              String color,
                              String size,
                              String location,
                              Date importDate,
                              Date exportDate) {
        this.id = id;
        this.shawl = ShawlResource.builder().name(shawlName).build();
        this.importCustomer = ShawlCustomerResource.builder().name(importCustomer == null ? "N/A": importCustomer).build();
        this.exportCustomer = ShawlCustomerResource.builder().name(exportCustomer == null ? "N/A" : exportCustomer).build();
        this.yarn = ShawlYarnResource.builder().name(yarn).build();
        this.color = ShawlColorResource.builder().name(color).build();
        this.size = ShawlSizeResource.builder().name(size).build();
        this.location = LocationResource.builder().name(location).build();
        this.importDate = importDate;
        this.exportDate = exportDate;
    }
}
