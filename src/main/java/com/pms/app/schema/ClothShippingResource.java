package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import java.security.acl.LastOwnerException;
import java.util.Date;

/**
 * Created by arjun on 9/18/16.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ClothShippingResource {
    private Long id;
    private String designName;
    private String sizeName;
    private String colorName;
    private Integer order_no;
    private Long count;
    private String boxNumber;


    @QueryProjection
    public ClothShippingResource(
            Long id,
            String designName,
            String sizeName,
            String colorName,
            Integer order_no,
            Long count,
            String boxNumber) {

        this.id = id;
        this.designName = designName;
        this.sizeName = sizeName;
        this.colorName = colorName;
        this.order_no = order_no;
        this.count = count;
        this.boxNumber = boxNumber;
    }
}