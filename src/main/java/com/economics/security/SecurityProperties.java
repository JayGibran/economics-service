package com.economics.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties()
public class SecurityProperties {

    @Value("${security-token-secret}")
    private String tokenSecret;

    @Value("#{new Long('${security-expiration-time}')}")
    private Long expirationTime;

    @Value("${security-token-prefix}")
    private String tokenPrefix;

    @Value("${security-header-string}")
    private String headerString;


}
