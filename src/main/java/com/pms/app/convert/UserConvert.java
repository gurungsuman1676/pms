package com.pms.app.convert;

import com.pms.app.domain.Users;
import com.pms.app.schema.UserResource;

import java.util.List;

public interface UserConvert {
    List<UserResource> convert(List<Users> users);

    UserResource convert(Users users);
}
