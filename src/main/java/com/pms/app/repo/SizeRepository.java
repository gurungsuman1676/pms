package com.pms.app.repo;

import com.pms.app.domain.Sizes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends AbstractRepository<Sizes> {

    Sizes findByName(String name);

    List<Sizes> findAllByOrderByNameAsc();
}
