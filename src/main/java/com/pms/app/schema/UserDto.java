package com.pms.app.schema;


import com.pms.app.domain.Roles;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Roles role;
    private Long location;
}
