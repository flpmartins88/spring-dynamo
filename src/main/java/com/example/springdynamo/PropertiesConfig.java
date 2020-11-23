package com.example.springdynamo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class PropertiesConfig {

}
