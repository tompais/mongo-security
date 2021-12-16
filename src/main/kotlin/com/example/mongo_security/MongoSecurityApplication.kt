package com.example.mongo_security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class MongoSecurityApplication

fun main(args: Array<String>) {
    runApplication<MongoSecurityApplication>(*args)
}
