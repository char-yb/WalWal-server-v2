package com.depromeet.walwal.global.config.security

import com.depromeet.walwal.domain.auth.application.JwtTokenService
import com.depromeet.walwal.global.common.constants.SwaggerUrlConstants
import com.depromeet.walwal.global.filter.JwtAuthenticationFilter
import com.depromeet.walwal.global.util.CookieUtil
import com.depromeet.walwal.infra.properties.SwaggerProperties
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
	private val jwtTokenService: JwtTokenService,
	private val cookieUtil: CookieUtil,
	private val swaggerProperties: SwaggerProperties,
) {

	@Bean
	fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
		val user =
			User.withUsername(swaggerProperties.user)
				.password(passwordEncoder().encode(swaggerProperties.password))
				.roles("SWAGGER")
				.build()
		return InMemoryUserDetailsManager(user)
	}

	@Bean
	@Throws(Exception::class)
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		apiSecurityFilterChain(http)

		http.authorizeHttpRequests { authorize ->
			authorize
				.requestMatchers("/walwal-actuator/**")
				.permitAll() // Actuator
				.requestMatchers("/auth/**")
				.permitAll() // Auth endpoints
				.anyRequest()
				.authenticated()
		}
			.exceptionHandling { exception: ExceptionHandlingConfigurer<HttpSecurity?> ->
				exception.authenticationEntryPoint { _: HttpServletRequest?, response: HttpServletResponse, _: AuthenticationException? ->
					response.status = 401
				}
			}
		http.addFilterBefore(
			jwtAuthenticationFilter(jwtTokenService, cookieUtil),
			UsernamePasswordAuthenticationFilter::class.java,
		)
		return http.build()
	}

	@Bean
	@Order(1)
	fun swaggerFilterChain(http: HttpSecurity): SecurityFilterChain {
		apiSecurityFilterChain(http)

		http.securityMatcher(SwaggerUrlConstants.getSwaggerUrls().toString()).httpBasic(withDefaults())

		http.authorizeHttpRequests { authorize -> authorize.anyRequest().permitAll() }

		return http.build()
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Throws(Exception::class)
	private fun apiSecurityFilterChain(http: HttpSecurity) {
		http
			.httpBasic { httpBasicConfigurer -> httpBasicConfigurer.disable() }
			.formLogin { formLoginConfigurer -> formLoginConfigurer.disable() }
			.csrf { csrfConfigurer -> csrfConfigurer.disable() }
			.cors(withDefaults())
			.sessionManagement { sessionManagementConfigurer ->
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			}
	}

	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource {
		val configuration = CorsConfiguration()
		// TODO: CORS 임시 전체 허용
		configuration.addAllowedOriginPattern("*")

		configuration.addAllowedHeader("*")
		configuration.addAllowedMethod("*")
		configuration.allowCredentials = true
		configuration.addExposedHeader(HttpHeaders.SET_COOKIE)

		val source = UrlBasedCorsConfigurationSource()
		source.registerCorsConfiguration("/**", configuration)
		return source
	}

	@Bean
	fun jwtAuthenticationFilter(
		jwtTokenService: JwtTokenService,
		cookieUtil: CookieUtil,
	): JwtAuthenticationFilter {
		return JwtAuthenticationFilter(jwtTokenService, cookieUtil)
	}
}
