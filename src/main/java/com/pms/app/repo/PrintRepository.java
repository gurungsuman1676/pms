package com.pms.app.repo;

import com.pms.app.domain.Prints;
import com.pms.app.repo.repoCustom.PrintRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintRepository extends AbstractRepository<Prints> ,PrintRepositoryCustom {

    @Query("SELECT p FROM Prints p WHERE p.name=:name AND p.size.id =:sizeId AND p.currency.id =:currencyId")
    Prints findByNameAndSizeIdAndCurrencyId(@Param("name") String name, @Param("sizeId") Long sizeId, @Param("currencyId") Long currencyId);

    @Query("SELECT p FROM Prints p WHERE p.size.id=:sizeId ORDER BY p.name ASC")
    List<Prints> findBySizeId(@Param("sizeId") Long sizeId);

    List<Prints> findAllByOrderByNameAsc();
}
