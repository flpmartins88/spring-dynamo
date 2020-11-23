package com.example.springdynamo

import org.springframework.stereotype.Component
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import java.time.LocalDate
import java.util.*

data class Person(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val birthDate: LocalDate
)

const val PERSON_TABLE = "person"
const val PERSON_ID_FIELD = "id"
const val PERSON_NAME_FIELD = "name"
const val PERSON_BIRTHDATE_FIELD = "birthdate"

@Component
class PersonRepository(private val dynamoDbClient: DynamoDbClient) {

    fun save(person: Person) {
        val putItemRequest = PutItemRequest.builder()
                .tableName(PERSON_TABLE)
                .item(person.toMap())
                .build()

        dynamoDbClient.putItem(putItemRequest)
                .also { println(it) }
    }

    fun findAll(): List<Person> {
        val scanRequest = ScanRequest.builder()
                .tableName(PERSON_TABLE)
                .build()

        return dynamoDbClient.scan(scanRequest)
                .items()
                .mapNotNull { map -> fromMap(map) }
    }

    fun findById(id: String): Person? {
        val getItemRequest = GetItemRequest.builder()
                .tableName(PERSON_TABLE)
                .key(mapOf(PERSON_ID_FIELD to AttributeValue.builder().s(id).build()))
                .build()

        return dynamoDbClient.getItem(getItemRequest)
                .item()
                .let { map -> fromMap(map) }
    }

    private fun Person.toMap() = mapOf(
            PERSON_ID_FIELD        to AttributeValue.builder().s(this.id).build(),
            PERSON_NAME_FIELD      to AttributeValue.builder().s(this.name).build(),
            PERSON_BIRTHDATE_FIELD to AttributeValue.builder().s(this.birthDate.toString()).build()
    )

    private fun fromMap(values: Map<String, AttributeValue>): Person? {
        val id = values[PERSON_ID_FIELD]?.s()
        val name = values[PERSON_NAME_FIELD]?.s()
        val birthDate = values[PERSON_BIRTHDATE_FIELD]?.s()
                ?.let { LocalDate.parse(it) }

        if (id == null || name == null || birthDate == null) {
            return null
        }

        return Person(id, name, birthDate)
    }
}

