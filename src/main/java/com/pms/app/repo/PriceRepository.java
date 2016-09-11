package com.pms.app.repo;

import com.pms.app.domain.Prices;
import com.pms.app.domain.Sizes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends AbstractRepository<Prices> {

    @Query("SELECT p FROM Prices p WHERE p.design.id =:designId AND p.size.id=:sizeId AND p.yarn.id =:yarnId")
    Prices findByDesignAndSizeAndYarn(@Param("designId") Long designId, @Param("sizeId") Long sizeId, @Param("yarnId") Long yarnId);

    @Query("SELECT p.size FROM Prices p WHERE p.design.id =:designId AND p.yarn.id =:yarnId ORDER BY p.size.name ASC")
    List<Sizes> findSizesByDesignIdAndYarnId(@Param("designId")Long designId,@Param("yarnId") Long yarnId);
}
