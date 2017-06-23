package com.pms.app.repo;

import com.pms.app.domain.Designs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DesignRepository extends AbstractRepository<Designs> {


    @Query("SELECT d FROM Designs d WHERE d.name =:name AND d.parent.id =:parentId AND d.customer.id =:customerId")
    Designs findByNameAndParentAndCustomer(@Param("name") String name, @Param("parentId") Long parentId, @Param("customerId") Long customerId);


    @Query("SELECT d FROM Designs d WHERE d.customer.id = :customerId AND d.parent IS NULL ORDER BY d.name ASC")
    List<Designs> findParentByCustomers(@Param("customerId") Long customerId);

    @Query("SELECT d FROM Designs d WHERE d.parent.id =:parentId ORDER BY d.name ASC")
    List<Designs> findByParent(@Param("parentId") Long parentId);

    @Query("SELECT d FROM Designs d WHERE d.parent IS NULL ORDER BY d.name ASC")
    List<Designs> findParentDesigns();

    List<Designs> findAllByOrderByNameAsc();

    @Query("SELECT d FROM Designs d WHERE d.name =:name  AND d.customer.id =:customerId")
    Designs findByNameAndCustomer(@Param("name") String name, @Param("customerId") Long customerId);


    @Query("SELECT d.id FROM Designs d WHERE d.name =:name  AND d.customer.id =:customerId")
    Long findIdByNameAndCustomer(@Param("name") String name, @Param("customerId") Long customerId);


}
