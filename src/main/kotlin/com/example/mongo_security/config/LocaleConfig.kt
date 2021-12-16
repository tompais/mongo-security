package com.example.mongo_security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale.UK

@Configuration
class LocaleConfig {
    @Bean
    fun localeResolver(): LocaleResolver = SessionLocaleResolver().apply {
        setDefaultLocale(UK)
    }
}
