package com.pms.app.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> {
    private Long totalElements;
    private int pageSize;
    private int pageNo;
    private List<T> data;

    public PageResult(Long totalElements,
                      int pageSize,
                      int pageNo,
                      List<T> data) {

        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.data = data;

    }
}
