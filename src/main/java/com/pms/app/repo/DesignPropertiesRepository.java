package com.pms.app.repo;

import com.pms.app.domain.DesignProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface DesignPropertiesRepository extends AbstractRepository<DesignProperties> {

    @Query("SELECT s FROM DesignProperties  s WHERE s.design.id =:designId AND s.active =:active ")
    List<DesignProperties> findAllByDesignIdAndActive(@Param("designId") Long designId, @Param("active") Boolean active);
}
