package com.example.springdynamo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * Nao esta funcionando e nao sei o pq, ele deveria gerar o arquivo com os dados para o intellij importar e resolver as configs
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    public String endpoint;
    public String region = "us-east-1";

}