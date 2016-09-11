package com.pms.app.schema;


import com.pms.app.domain.Roles;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

import java.util.List;

@Getter
@Setter
@Builder
public class UserResource {
    private Long id;
    private String username;
    private Roles role;
    private String location;
}
