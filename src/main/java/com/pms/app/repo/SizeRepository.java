package com.pms.app.repo;

import com.pms.app.domain.Sizes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends AbstractRepository<Sizes> {

    Sizes findByName(String name);

    List<Sizes> findAllByOrderByNameAsc();

    @Query("SELECT s.id FROM Sizes s WHERE s.name =:name")
    Long findIdByName(@Param("name") String sizeName);
}
