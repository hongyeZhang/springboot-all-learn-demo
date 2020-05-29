package com.zhq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties
public class OrgProperties {
    private List<String> orgs;
}
