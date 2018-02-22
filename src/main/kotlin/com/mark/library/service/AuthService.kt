package com.mark.library.service

import org.openstack4j.model.common.Identifier
import org.openstack4j.openstack.OSFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class KeystoneAuthenticationProvider(
        @Value("\${keystone.endpoint}") private val ksEndpoint: String,
        @Value("\${keystone.domain}") private val ksDomain: String,
        @Value("\${keystone.project}") private val ksProject: String) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()

        val user = OSFactory.builderV3()
                .endpoint(ksEndpoint)
                .credentials(username, password, Identifier.byId(ksDomain))
                .scopeToProject(Identifier.byName(ksProject), Identifier.byId(ksDomain))
                .authenticate()
        return UsernamePasswordAuthenticationToken(
                username, password,
                user.token.roles.map { role ->
                    SimpleGrantedAuthority("ROLE_${role.name}")
                })
    }

    override fun supports(authentication: Class<*>): Boolean =
            UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
}
