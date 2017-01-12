package com.pms.app.repo;

import com.pms.app.domain.Colors;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ColorRepository extends AbstractRepository<Colors> {


    @Query("SELECT c FROM Colors c WHERE c.name_supplier=:name_supplier AND c.yarn.id =:yarnId AND c.code =:code")
    Colors findByName_companyAndYarnAndCode(@Param("name_supplier") String name, @Param("yarnId") Long yarnId, @Param("code") String code);

    @Query("SELECT c FROM Colors c WHERE c.yarn.id =:yarnId ORDER BY c.name_company ASC")
    List<Colors> findByYarn(@Param("yarnId") Long yarnId);

    @Query("SELECT c FROM Colors c  ORDER BY c.name_company ASC")
    List<Colors> findAllByOrderByName_companyAsc();

}
