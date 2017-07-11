package com.pms.app.repo;

import com.pms.app.domain.Sizes;
import com.pms.app.repo.repoCustom.SizeRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends AbstractRepository<Sizes>,SizeRepositoryCustom {

    Sizes findByName(String name);

    List<Sizes> findAllByOrderByNameAsc();

}
