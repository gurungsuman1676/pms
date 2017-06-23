package com.pms.app.domain;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity extends AbstractPersistable<Long> {

    @JsonIgnore
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @CreatedDate
    private Date created;


    @JsonIgnore
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @LastModifiedDate
    private Date lastModified;

    @Version
    private Long version;


    @JsonIgnore
    @Override
    public boolean isNew() {
        return super.isNew();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @PrePersist
    public void onPersist(){
        this.created = new Date();
        this.lastModified = new Date();
    }
    @PreUpdate
    public void onUpdate(){
        this.lastModified = new Date();
    }

}
