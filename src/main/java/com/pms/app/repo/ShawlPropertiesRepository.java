package com.pms.app.repo;

import com.mysema.query.annotations.QueryInit;
import com.pms.app.domain.ShawlProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlPropertiesRepository extends AbstractRepository<ShawlProperties> {

    @Query("SELECT s FROM ShawlProperties  s WHERE s.shawl.id =:shawlId AND s.active =:active ")
    List<ShawlProperties> findAllByShawlIdAndActive(@Param("shawlId") Long shawlId,@Param("active") Boolean active);
}
