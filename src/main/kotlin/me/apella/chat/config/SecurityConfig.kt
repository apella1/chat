package me.apella.chat.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/auth/**", permitAll)
                authorize("/v3/api-docs", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-resources", permitAll)
                authorize("/swagger-resources/**", permitAll)
                authorize("/configuration/ui", permitAll)
                authorize("/configuration/security", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/webjars/**", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize("/ws/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            httpBasic { }
            cors { }
            csrf { disable() }
            oauth2ResourceServer {
                jwt {
                    jwtAuthenticationConverter = KeycloakJwtAuthenticationConverter()
                }
            }
        }
        return http.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().apply {
            allowCredentials
            allowedOriginPatterns = listOf("http://localhost:3000")
            allowedHeaders = listOf(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
            )
            allowedMethods = listOf("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
        }

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}