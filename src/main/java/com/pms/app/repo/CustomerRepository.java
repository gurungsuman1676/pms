package com.pms.app.repo;

import com.pms.app.domain.Currency;
import com.pms.app.domain.Customers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends AbstractRepository<Customers> {

    @Query("SELECT c FROM Customers c WHERE c.name=:name AND c.parent.id =:parentId ORDER BY c.name ASC")
    Customers findByNameAndParentId(@Param("name")String name,@Param("parentId") Long parentId);

    @Query("SELECT c FROM Customers c WHERE c.parent IS NULL ORDER BY c.name ASC")
    List<Customers> findParentCustomers();

    @Query("SELECT c FROM Customers c WHERE c.parent =:parentId ORDER BY c.name ASC")
    List<Customers> findCustomerByParentId(@Param("parentId")Long parentId);

    List<Customers> findAllByOrderByNameAsc();

    @Query("SELECT c.id FROM Customers c WHERE c.name =:name")
    Long findCustomeIdByName(@Param("name") String name);

    @Query("SELECT c FROM Customers c WHERE c.name =:name")
    Customers findByName(@Param("name") String name);

    @Query("SELECT c.currency FROM Customers c WHERE c.id =:id")
    Currency findCurrencyByCustomer(@Param("id") Long id);

    @Query("SELECT cp.id FROM Customers c  LEFT JOIN c.parent cp WHERE c.id =:id")
    Long findCustomerParentById(@Param("id") Long customerId);
}
