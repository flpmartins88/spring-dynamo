package com.example.springdynamo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.LocalDate

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/person")
class PersonController(
        private val personRepository: PersonRepository
) {

    @PostMapping
    fun save(@RequestBody person: PersonRequest): ResponseEntity<Person> {
        return ResponseEntity.created(URI.create("/person")).body(null)
    }

}

private fun PersonRequest.toDomain(): Person =
        Person(name = this.name!!, birthDate = this.birthDate!!)

data class PersonRequest(
        @NotNull @NotBlank
        val name: String?,

        @NotNull
        val birthDate: LocalDate?
)

data class PersonResponse(
        val id: String,
        val name: String,
        val birthDate: LocalDate
)