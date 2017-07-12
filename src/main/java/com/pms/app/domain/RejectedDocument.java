package com.pms.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by arjun on 7/11/2017.
 */

@Getter
@Setter
@Entity
public class RejectedDocument extends AbstractEntity {

    private String docPath;

    @OneToOne
    @JoinColumn(name = "log_id")
    private WeavingWorkLog weavingWorkLog;
}
