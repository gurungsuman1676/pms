package com.pms.app.schema;

import com.pms.app.domain.Shawl;
import com.pms.app.domain.ShawlColor;
import com.pms.app.domain.ShawlCustomer;
import com.pms.app.domain.ShawlSize;
import com.pms.app.domain.ShawlYarn;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by arjun on 6/27/2017.
 */

@Getter
@Setter
public class ShawlEntryDto {

    private int quantity;

    private Long shawlSizeId;

    private Long shawlId;

    private Long shawlYarnId;

    private Long shawlColorId;

    private Long shawlCustomerId;

    private Long locationId;
}
