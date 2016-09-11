package com.pms.app.repo;

import com.pms.app.domain.Users;

public interface UserRepository extends AbstractRepository<Users> {
    Users findByUsername(String username);
}
