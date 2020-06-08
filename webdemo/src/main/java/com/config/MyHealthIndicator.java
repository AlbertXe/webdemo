package com.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return new Health.Builder().withDetail("username", "albert").withDetail("status", "up").up().build();
    }
}
