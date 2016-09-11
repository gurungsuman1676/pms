package com.pms.app.schedule;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.datasource")
public class DatabaseSetting {

    @NotBlank
    private String driverClassName;

    @NotBlank
    private String url;

    @NotBlank
    private String username;

    private String password;
}
