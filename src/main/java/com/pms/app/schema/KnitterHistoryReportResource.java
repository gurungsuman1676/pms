package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by arjun on 6/22/2017.
 */

@Data
@NoArgsConstructor
public class KnitterHistoryReportResource {
    private Date completedOn;
    private Date deliveryDate;
    private String knitterName;
    private String machineName;
    private Integer orderNo;
    private String customerName;
    private String designName;
    private String yarnName;
    private String sizeName;
    private Double gauge;
    private String setting;
    private Boolean reOrder;

    @QueryProjection
    public KnitterHistoryReportResource(Date completedOn,
                                        Date deliveryDate,
                                        String knitterName,
                                        String machineName,
                                        Integer orderNo,
                                        String customerName,
                                        String designName,
                                        String yarnName,
                                        String sizeName,
                                        Double gauge,
                                        String setting,
                                        Boolean reOrder) {

        this.completedOn = completedOn;
        this.deliveryDate = deliveryDate;
        this.knitterName = knitterName;
        this.machineName = machineName;
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.designName = designName;
        this.yarnName = yarnName;
        this.sizeName = sizeName;
        this.gauge = gauge;
        this.setting = setting;
        this.reOrder = reOrder;
    }
}
