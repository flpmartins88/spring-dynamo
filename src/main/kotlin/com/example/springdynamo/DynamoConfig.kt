package com.example.springdynamo

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder
import java.net.URI

@Configuration
class DynamoConfig(
        @Value("\${aws.endpoint}")
        private val awsEndpoint: String?,
        @Value("\${aws.region}")
        private val awsRegion: String?
) {

    @Bean
    fun buildDynamoClient(credentialsProvider: AwsCredentialsProvider): DynamoDbClient {
        val builder = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)

        if (awsEndpoint != null) {
            builder.endpointOverride(URI(awsEndpoint))
        }

        if (awsRegion != null) {
            val region = awsRegion.let { Region.of(it) } ?: Region.US_EAST_1
            builder.region(region)
        }

        return builder.build()
    }

    @Bean
    @Profile("localstack")
    fun localstackCredentialsProvider(): AwsCredentialsProvider =
            StaticCredentialsProvider.create(AwsBasicCredentials.create("a", "a"))


    @Bean
    @Profile("!localstack")
    fun profileCredentialsProvider(): AwsCredentialsProvider = ProfileCredentialsProvider.builder()
            .profileName("pessoal")
            .build()

    fun buildDefaultDynamoClient(): DynamoDbClient {
        return DynamoDbClient.create()
    }

    @Bean
    fun buildDynamoAsyncClient(credentialsProvider: AwsCredentialsProvider): DynamoDbAsyncClient {
        return DynamoDbAsyncClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build()
    }

}
