package com.alpaca.buddymarket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class BuddyMarketApplication

fun main(args: Array<String>) {
    runApplication<BuddyMarketApplication>(*args)
}

@Configuration
@EnableJpaAuditing
class AuditingConfig
