package com.pms.app.repo;

import com.pms.app.domain.Locations;
import com.pms.app.domain.UserLocations;
import com.pms.app.domain.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLocationRepository extends AbstractRepository<UserLocations> {

    @Query("SELECT u.location FROM UserLocations u WHERE u.user =:user ORDER BY u.location.name ASC")
    List<Locations> findLocationByUser(@Param("user")Users user);
}
