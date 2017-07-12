package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by arjun on 7/11/2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class ShawlInventoryBatchDetailResource {
    private Date created;
    private Integer count;
    private Integer remainingCount;
    private boolean isEntry;


    @QueryProjection
    public ShawlInventoryBatchDetailResource(Date created, Integer count, Integer remainingCount, boolean isEntry) {
        this.created = created;
        this.count = count;
        this.remainingCount = remainingCount;
        this.isEntry = isEntry;
    }
}
