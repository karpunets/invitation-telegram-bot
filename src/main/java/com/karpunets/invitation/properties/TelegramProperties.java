package com.karpunets.invitation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram")
@Data
public class TelegramProperties {
    private String bot;
    private String webhook;
    private String token;
    private String notifyUserId;

}
