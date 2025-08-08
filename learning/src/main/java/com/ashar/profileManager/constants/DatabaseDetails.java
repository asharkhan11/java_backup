package com.ashar.profileManager.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseDetails {

    private String url;
    private String username;
    private String driverClassName;
}
