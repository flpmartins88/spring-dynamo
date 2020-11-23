package com.example.springdynamo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import java.time.LocalDate

@SpringBootApplication
class SpringDynamoApplication(private val personRepository: PersonRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println("Inserindo dado na tabela 'person'")
        println("-----------------------------------------------------------------------------------------------------")
        val person = Person(name = "Felipe", birthDate = LocalDate.of(1988, 8, 7))
        personRepository.save(person)
        println("-----------------------------------------------------------------------------------------------------")

        println("Listando dados da tabela 'person'")
        println("-----------------------------------------------------------------------------------------------------")
        personRepository.findAll().forEach { p -> println(p) }
        println("-----------------------------------------------------------------------------------------------------")
    }

}

fun main(args: Array<String>) {
    runApplication<SpringDynamoApplication>(*args)
}
