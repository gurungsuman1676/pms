package com.pms.app.repo;

import com.pms.app.domain.AbstractEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AbstractRepository<T extends AbstractEntity> extends PagingAndSortingRepository<T, Long>,
        QueryDslPredicateExecutor<T> {
}
