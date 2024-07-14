package com.depromeet.walwal.global.config.security

import com.depromeet.walwal.global.common.constants.SwaggerUrlConstants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.config.Customizer.*
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers("/walwal-actuator/**")
                .permitAll() // 액추에이터
                .anyRequest()
                .authenticated()
        }

        return http.build()
    }

    @Bean
    @Order(1)
    @Throws(Exception::class)
    fun swaggerFilterChain(http: HttpSecurity): SecurityFilterChain {
        defaultFilterChain(http)

//        http.securityMatcher(SwaggerUrlConstants.getSwaggerUrls()).httpBasic(withDefaults())
        http.securityMatcher(SwaggerUrlConstants.getSwaggerUrls().toString()).httpBasic(withDefaults())

        http.authorizeHttpRequests { authorize -> authorize.anyRequest().permitAll() }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    private fun defaultFilterChain(http: HttpSecurity) {
        http.httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .cors(withDefaults())
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration: CorsConfiguration = CorsConfiguration()
        // TODO: CORS 임시 전체 허용
        configuration.addAllowedOriginPattern("*")

        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        configuration.setAllowCredentials(true)
        configuration.addExposedHeader(HttpHeaders.SET_COOKIE)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
