package me.apella.chat.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter


class KeycloakJwtAuthenticationConverter : Converter<Jwt, AbstractAuthenticationToken> {
    override fun convert(source: Jwt): AbstractAuthenticationToken? {
        val defaultAuthorities = JwtGrantedAuthoritiesConverter().convert(source).orEmpty()
        val resourceRoles = extractResourceRoles(source)
        return JwtAuthenticationToken(source, defaultAuthorities + resourceRoles)
    }

    private fun extractResourceRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = jwt.getClaim<Map<String, Any>>("resource_access") ?: emptyMap()
        val account = resourceAccess["account"] as? Map<String, List<String>> ?: emptyMap()
        val roles = account["roles"].orEmpty()
        return roles.map { role ->
            SimpleGrantedAuthority("ROLE_" + role.replace("-", "_"))
        }.toSet()
    }
}