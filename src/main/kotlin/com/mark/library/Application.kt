package com.mark.library

import com.mark.library.service.KeystoneAuthenticationProvider
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.inject.Inject


@SpringBootApplication
class Application

@EnableWebSecurity
@EnableCaching
@EnableScheduling
@EnableSwagger2
@Import(SpringDataRestConfiguration::class)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Inject
    lateinit var authProvider: KeystoneAuthenticationProvider

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "**").hasAnyRole("member", "guest")
                .anyRequest().hasRole("member")
                .and()
                .httpBasic()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }
}

fun main(args: Array<String>) {
    SpringApplication(Application::class.java).run(*args)
}
