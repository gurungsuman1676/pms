package com.pms.app.repo.repoImpl;

import com.pms.app.domain.AbstractEntity;
import com.pms.app.repo.AbstractRepository;
import com.pms.app.repo.repoCustom.AbstractRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;


public abstract class AbstractRepositoryImpl<T extends AbstractEntity, R extends AbstractRepository<T>>
        extends QueryDslRepositorySupport implements AbstractRepositoryCustom<T> {

    protected R repository;

    public AbstractRepositoryImpl(Class<T> domainClass) {
        super(domainClass);
    }

    @PostConstruct
    public void validateBeans() {
        Assert.notNull(repository, "Repository must not be null.\nAutowire repository with Setter Injection.");
    }
}