package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Login {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
