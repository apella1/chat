package me.apella.chat

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
    name = "keycloak",
    type = SecuritySchemeType.OAUTH2,
    bearerFormat = "JWT",
    scheme = "bearer",
    `in` = SecuritySchemeIn.HEADER,
    flows = OAuthFlows(
        password = OAuthFlow(
            authorizationUrl = "http://localhost:9090/realms/chat/protocol/openid-connect/auth",
            tokenUrl = "http://localhost:9090/realms/chat/protocol/openid-connect/token"
        )
    )
)
class ChatApplication

fun main(args: Array<String>) {
    runApplication<ChatApplication>(*args)
}
