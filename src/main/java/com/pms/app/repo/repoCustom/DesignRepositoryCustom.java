package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Designs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by arjun on 7/11/2017.
 */
public interface DesignRepositoryCustom extends AbstractRepositoryCustom<Designs> {
    Long findIdByNameAndCustomer(String name, Long customerId);
}
