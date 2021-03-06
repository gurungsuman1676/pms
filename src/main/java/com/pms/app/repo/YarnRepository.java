package com.pms.app.repo;

import com.pms.app.domain.Yarns;
import com.pms.app.repo.repoCustom.YarnRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface YarnRepository extends AbstractRepository<Yarns>,YarnRepositoryCustom {

    List<Yarns> findAllByOrderByNameAsc();

    Yarns findByName(String name);
}
