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
    private ShawlColorResource color;
    private SizeResource size;
    private Integer count;

    @QueryProjection
    public ShawlInventoryResource(Long id,
                                  String designName,
                                  String color,
                                  String size,
                                  Integer count) {
        this.id = id;
        this.design = DesignResource.builder().name(designName).build();
        this.color = ShawlColorResource.builder().name(color).build();
        this.size = SizeResource.builder().name(size).build();
        this.count = count;
    }
}
